package com.tlc.cansingtone.domain.chart;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class PersonalizedChart {
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

    @Column(name = "karaoke_num")
    private String karaoke_num;

    @Column(name = "prefer_age", nullable = false)
    private int preferAge;

    @Column(name = "prefer_gender", nullable = false)
    private int preferGender;
}
