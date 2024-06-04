package com.tlc.cansingtone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "gender", nullable = false)
    private int gender;

    @Column(name = "ages", nullable = false)
    private int ages;

    @Column(name = "pref_genre1")
    private int pref_genre1;

    @Column(name = "pref_genre2")
    private int pref_genre2;

    @Column(name = "pref_genre3")
    private int pref_genre3;

    @Column(name = "vocal_range_high")
    private int vocalRangeHigh;

    @Column(name = "vocal_range_low")
    private int vocalRangeLow;

//    @Column(name = "timbre")
//    private String timbre;
}
