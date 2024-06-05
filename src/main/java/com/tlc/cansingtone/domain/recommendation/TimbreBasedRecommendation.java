package com.tlc.cansingtone.domain.recommendation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "timbre_based_recommendation")
@Getter
@Setter
public class TimbreBasedRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id", nullable = false)
    private Long recommendationId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "recommendation_date", nullable = false)
    private String recommendationDate;

    @Column(name = "timbre_id", nullable = false)
    private Long timbreId;

    @Column(name = "song_ids", nullable = false)
    private String songIds;

}
