package com.tlc.cansingtone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "playlist")
@Getter
@Setter
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id", nullable = false)
    private Long playlistId;

    @JoinColumn(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "playlist_name", nullable = false)
    private String playlistName;

    @Column(name = "is_public", nullable = false)
    private int isPublic;
}
