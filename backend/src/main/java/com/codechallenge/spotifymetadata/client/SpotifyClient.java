package com.codechallenge.spotifymetadata.client;

import com.codechallenge.spotifymetadata.config.SpotifyProperties;
import com.codechallenge.spotifymetadata.dto.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Slf4j
@Service
public class SpotifyClient {

    private final WebClient webClient;
    private final SpotifyProperties spotifyProperties;
    private String accessToken;

    public SpotifyClient(WebClient.Builder webClientBuilder, SpotifyProperties spotifyProperties) {
        this.webClient = webClientBuilder.build();
        this.spotifyProperties = spotifyProperties;
    }

    // Necesitamos un record para mapear la respuesta del token
    private record SpotifyTokenResponse(@JsonProperty("access_token") String accessToken) {}

    private Mono<String> getAccessToken() {
        if (this.accessToken != null) {
            // Devolvemos el token cacheado si ya lo tenemos
            return Mono.just(this.accessToken);
        }

        log.info("Requesting new Spotify access token...");
        String authHeader = "Basic " + Base64.getEncoder().encodeToString(
                (spotifyProperties.getClientId() + ":" + spotifyProperties.getClientSecret()).getBytes()
        );

        return this.webClient.post()
                .uri("https://accounts.spotify.com/api/token")
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(SpotifyTokenResponse.class)
                .map(tokenResponse -> {
                    log.info("New Spotify token acquired.");
                    this.accessToken = tokenResponse.accessToken();
                    return this.accessToken;
                });
    }

    public Mono<SpotifyTrack> searchTrackByIsrc(String isrc) {
        return getAccessToken().flatMap(token ->
                this.webClient.get()
                        .uri("https://api.spotify.com/v1/search?q=isrc:{isrc}&type=track", isrc)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .retrieve()
                        .bodyToMono(SpotifySearchResponse.class)
                        .map(response -> {
                            if (response.tracks() == null || response.tracks().items().isEmpty()) {
                                // Aquí podrías lanzar una excepción personalizada de "no encontrado"
                                throw new RuntimeException("Track not found for ISRC: " + isrc);
                            }
                            return response.tracks().items().get(0); // Tomamos solo el primer resultado
                        })
        );
    }

    public Mono<byte[]> downloadImage(String url) {
        return this.webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(byte[].class);
    }

}
