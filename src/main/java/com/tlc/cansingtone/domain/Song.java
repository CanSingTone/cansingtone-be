package com.tlc.cansingtone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "song")
@Getter
@Setter
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id", nullable = false)
    private Long songId;

    @Column(name = "song_title", nullable = false)
    private String songTitle;

    @Column(name = "album_image")
    private String albumImage;

    @Column(name = "artist", nullable = false)
    private String artist;

    @Column(name = "genre", nullable = false)
    private int genre;

    @Column(name = "artist_gender", nullable = false)
    private int artistGender;

    @Column(name = "song_vid_url", nullable = false)
    private String songVidUrl;

    @Column(name = "mr_vid_url", nullable = false)
    private String mrVidUrl;

    @Column(name = "highest_note", nullable = false)
    private int highestNote;

    @Column(name = "lowest_note", nullable = false)
    private int lowestNote;

    @Column(name = "karaoke_num", nullable = false)
    private String karaoke_num;
}
