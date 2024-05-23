package com.tlc.cansingtone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "`like`")
@Getter
@Setter
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id", nullable = false)
    private Long likeId;

    @JoinColumn(name = "user_id", nullable = false)
    private String userId;

    @JoinColumn(name = "song_id", nullable = false)
    private Long songId;
}
