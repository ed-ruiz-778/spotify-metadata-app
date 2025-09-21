package com.codechallenge.spotifymetadata.dto;

import java.util.List;

public record SpotifyAlbum(String id, String name, List<SpotifyImage> images) {}
