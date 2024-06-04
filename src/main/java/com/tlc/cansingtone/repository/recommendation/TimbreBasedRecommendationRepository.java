package com.tlc.cansingtone.repository.recommendation;

import com.tlc.cansingtone.domain.recommendation.TimbreBasedRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimbreBasedRecommendationRepository extends JpaRepository<TimbreBasedRecommendation, Long> {
    List<TimbreBasedRecommendation> findByUserId(String userId);
}
