package com.codechallenge.spotifymetadata.service;

import com.codechallenge.spotifymetadata.client.SpotifyClient;
import com.codechallenge.spotifymetadata.dto.TrackMetadataDto;
import com.codechallenge.spotifymetadata.exception.ResourceAlreadyExistsException;
import com.codechallenge.spotifymetadata.exception.ResourceNotFoundException;
import com.codechallenge.spotifymetadata.model.Track;
import com.codechallenge.spotifymetadata.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    private final SpotifyClient spotifyClient;

    @Transactional
    public TrackMetadataDto createTrackFromIsrc(String isrc) {
        if (trackRepository.existsByIsrc(isrc)) {
            log.warn("Attempted to create a track with an existing ISRC: {}", isrc);
            throw new ResourceAlreadyExistsException("Track with ISRC " + isrc + " already exists.");
        }

        var spotifyTrack = spotifyClient.searchTrackByIsrc(isrc).block();

        if (spotifyTrack == null) {
            throw new ResourceNotFoundException("Track not found on Spotify for ISRC: " + isrc);
        }

        String imageUrl = spotifyTrack.album().images().stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No cover image found for album " + spotifyTrack.album().id()))
                .url();

        byte[] coverImage = spotifyClient.downloadImage(imageUrl).block();

        if (coverImage == null || coverImage.length == 0) {
            throw new IllegalStateException("Failed to download cover image from URL: " + imageUrl);
        }

        Track track = new Track();
        track.setIsrc(isrc);
        track.setName(spotifyTrack.name());
        track.setArtistName(spotifyTrack.artists().get(0).name()); // Simplificamos al primer artista
        track.setAlbumName(spotifyTrack.album().name());
        track.setAlbumId(spotifyTrack.album().id());
        track.setExplicit(spotifyTrack.explicit());
        track.setPlaybackSeconds(spotifyTrack.durationMs() / 1000);
        track.setCoverImage(coverImage);

        Track savedTrack = trackRepository.save(track);
        log.info("Successfully created and stored track with ISRC: {}", isrc);

        return new TrackMetadataDto(
                savedTrack.getIsrc(),
                savedTrack.getName(),
                savedTrack.getArtistName(),
                savedTrack.getAlbumName(),
                savedTrack.isExplicit(),
                savedTrack.getPlaybackSeconds()
        );
    }

    @Transactional(readOnly = true)
    public TrackMetadataDto getTrackMetadataByIsrc(String isrc) {
        return trackRepository.findByIsrc(isrc)
                .map(track -> new TrackMetadataDto(
                        track.getIsrc(),
                        track.getName(),
                        track.getArtistName(),
                        track.getAlbumName(),
                        track.isExplicit(),
                        track.getPlaybackSeconds()))
                .orElseThrow(() -> new ResourceNotFoundException("Track with ISRC " + isrc + " not found in database."));
    }

    @Transactional(readOnly = true)
    public byte[] getTrackCoverByIsrc(String isrc) {
        Track track = trackRepository.findByIsrc(isrc)
                .orElseThrow(() -> new ResourceNotFoundException("Track with ISRC " + isrc + " not found in database."));

        if (track.getCoverImage() == null) {
            throw new ResourceNotFoundException("Cover image not found for track with ISRC: " + isrc);
        }

        return track.getCoverImage();
    }
}
