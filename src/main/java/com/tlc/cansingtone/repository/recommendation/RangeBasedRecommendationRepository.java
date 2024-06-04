package com.tlc.cansingtone.repository.recommendation;

import com.tlc.cansingtone.domain.recommendation.RangeBasedRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RangeBasedRecommendationRepository extends JpaRepository<RangeBasedRecommendation, Long> {
    List<RangeBasedRecommendation> findByUserId(String userId);

}
