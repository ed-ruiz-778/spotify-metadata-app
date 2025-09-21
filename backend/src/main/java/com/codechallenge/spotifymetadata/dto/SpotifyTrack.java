package com.codechallenge.spotifymetadata.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SpotifyTrack(
        String name,
        boolean explicit,
        @JsonProperty("duration_ms") int durationMs,
        SpotifyAlbum album,
        List<SpotifyArtist> artists
) {}
