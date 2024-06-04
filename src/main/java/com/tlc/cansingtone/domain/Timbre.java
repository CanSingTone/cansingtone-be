package com.tlc.cansingtone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "timbre")
@Getter
@Setter
public class Timbre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timbre_id", nullable = false)
    private Long timbreId;

    @Column(name = "timbre_name")
    private String timbreName;

    @Column(name = "timbre_url")
    private String timbreUrl;

    @Column(name = "user_id", nullable = false)
    private String userId;
}
