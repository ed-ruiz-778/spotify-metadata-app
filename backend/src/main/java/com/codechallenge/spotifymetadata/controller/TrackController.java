package com.codechallenge.spotifymetadata.controller;

import com.codechallenge.spotifymetadata.dto.TrackMetadataDto;
import com.codechallenge.spotifymetadata.service.TrackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/codechallenge")
@RequiredArgsConstructor
@Tag(name = "Track Metadata API", description = "API for fetching and retrieving Spotify track metadata")
@CrossOrigin(origins = "http://localhost:5173")
public class TrackController {

    private final TrackService trackService;

    @Operation(summary = "Create a track record from an ISRC", description = "Fetches track metadata and cover art from Spotify using an ISRC, stores it in the database, and returns the metadata.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Track created successfully"),
            @ApiResponse(responseCode = "409", description = "Track with this ISRC already exists"),
            @ApiResponse(responseCode = "404", description = "Track not found on Spotify for the given ISRC")
    })
    @PostMapping("/createTrack")
    public ResponseEntity<TrackMetadataDto> createTrack(
            @Parameter(description = "The International Standard Recording Code of the track.", required = true, example = "USMC18620549")
            @RequestParam String isrc) {
        TrackMetadataDto createdTrack = trackService.createTrackFromIsrc(isrc);
        // Devolvemos un 201 Created con la ubicación del nuevo recurso de metadata
        URI location = URI.create("/codechallenge/getTrackMetadata?isrc=" + createdTrack.isrc());
        return ResponseEntity.created(location).body(createdTrack);
    }

    @Operation(summary = "Get track metadata by ISRC")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Metadata found"),
            @ApiResponse(responseCode = "404", description = "Track not found in the database")
    })
    @GetMapping("/getTrackMetadata")
    public ResponseEntity<TrackMetadataDto> getTrackMetadata(@RequestParam String isrc) {
        return ResponseEntity.ok(trackService.getTrackMetadataByIsrc(isrc));
    }

    @Operation(summary = "Get track cover image by ISRC")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cover image found"),
            @ApiResponse(responseCode = "404", description = "Track or cover image not found in the database")
    })
    @GetMapping("/getCover")
    public ResponseEntity<byte[]> getCover(@RequestParam String isrc) {
        byte[] coverImage = trackService.getTrackCoverByIsrc(isrc);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Asumimos JPEG, podrías hacerlo más dinámico
                .body(coverImage);
    }
}
