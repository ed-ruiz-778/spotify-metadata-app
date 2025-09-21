package com.codechallenge.spotifymetadata.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@Table(name = "tracks", indexes = { @Index(name = "idx_isrc", columnList = "isrc", unique = true) })
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String isrc;

    @Column(nullable = false)
    private String name;

    private String artistName;
    private String albumName;
    private String albumId;
    private boolean isExplicit;
    private int playbackSeconds;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "cover_image", columnDefinition = "BYTEA")
    private byte[] coverImage;
}
