package com.tlc.cansingtone.domain.recommendation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "range_based_recommendation")
@Getter
@Setter
public class RangeBasedRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id", nullable = false)
    private Long recommendationId;

//    @Column(name = "song_id", nullable = false)
//    private Long songId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "recommendation_date", nullable = false)
    private String recommendationDate;

    @Column(name = "song_ids", nullable = false)
    private String songIds;

}

