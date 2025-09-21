package com.codechallenge.spotifymetadata.dto;

public record TrackMetadataDto(
        String isrc,
        String name,
        String artistName,
        String albumName,
        boolean isExplicit,
        int playbackSeconds
) {}
