package com.tlc.cansingtone.repository.recommendation;

import com.tlc.cansingtone.domain.recommendation.CombinedRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CombinedRecommendationRepository extends JpaRepository<CombinedRecommendation, Long> {
    List<CombinedRecommendation> findByUserId(String userId);
}
