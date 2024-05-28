package com.tlc.cansingtone.repository;

import com.tlc.cansingtone.domain.Playlist;
import com.tlc.cansingtone.domain.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findAll();

    List<Recommendation> findByUserIdAndRecommendationMethod(String userId, int recommendationMethod);
}
