package com.tlc.cansingtone.service.recommendation;

import com.tlc.cansingtone.domain.recommendation.CombinedRecommendation;
import com.tlc.cansingtone.domain.Song;

import com.tlc.cansingtone.dto.recommendation.ResCombinedRecommendationDto;
import com.tlc.cansingtone.repository.recommendation.CombinedRecommendationRepository;
import com.tlc.cansingtone.repository.SongRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CombinedRecommendationService {
    private final CombinedRecommendationRepository combinedRecommendationRepository;
    private final SongRepository songRepository;

    public CombinedRecommendationService(CombinedRecommendationRepository combinedRecommendationRepository, SongRepository songRepository) {
        this.combinedRecommendationRepository = combinedRecommendationRepository;
        this.songRepository = songRepository;
    }

    public Long createNewRecommendation(Long songId, String userId, String recommendationDate) {
        CombinedRecommendation newRecommendation = new CombinedRecommendation();
        newRecommendation.setSongId(songId);
        newRecommendation.setUserId(userId);
        newRecommendation.setRecommendationDate(recommendationDate);

        CombinedRecommendation savedRecommendation = combinedRecommendationRepository.save(newRecommendation);
        return savedRecommendation.getRecommendationId();
    }

    public List<ResCombinedRecommendationDto> getVocalRangeRecommendationsByUserId(String userId) {
        // 특정 사용자의 종합 추천 목록 조회

        List<CombinedRecommendation> recommendations = combinedRecommendationRepository.findByUserId(userId);

        List<ResCombinedRecommendationDto> recommendationsWithDetails = new ArrayList<>();

        for (CombinedRecommendation recommendation : recommendations) {
            Song song = songRepository.findById(recommendation.getSongId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
            recommendationsWithDetails.add(new ResCombinedRecommendationDto(recommendation, song));
        }

        return recommendationsWithDetails;
    }


}
