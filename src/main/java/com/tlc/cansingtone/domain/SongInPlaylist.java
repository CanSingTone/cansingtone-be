package com.tlc.cansingtone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "song_in_playlist")
@Getter
@Setter
public class SongInPlaylist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_in_playlist_id", nullable = false)
    private Long songInPlaylistId;

    @JoinColumn(name = "playlist_id", nullable = false)
    private Long playlistId;

    @JoinColumn(name = "song_id", nullable = false)
    private Long songId;
}
