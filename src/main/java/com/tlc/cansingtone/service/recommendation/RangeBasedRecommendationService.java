package com.tlc.cansingtone.service.recommendation;

import com.tlc.cansingtone.domain.recommendation.RangeBasedRecommendation;
import com.tlc.cansingtone.domain.Song;

import com.tlc.cansingtone.dto.recommendation.ResRangeBasedRecommendationDto;
import com.tlc.cansingtone.repository.recommendation.RangeBasedRecommendationRepository;
import com.tlc.cansingtone.repository.SongRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RangeBasedRecommendationService {
    private final RangeBasedRecommendationRepository rangeBasedRecommendationRepository;
    private final SongRepository songRepository;

    public RangeBasedRecommendationService(RangeBasedRecommendationRepository rangeBasedRecommendationRepository, SongRepository songRepository) {
        this.rangeBasedRecommendationRepository = rangeBasedRecommendationRepository;
        this.songRepository = songRepository;
    }

    public Long createNewRecommendation(Long songId, String userId, String recommendationDate) {
        RangeBasedRecommendation newRecommendation = new RangeBasedRecommendation();
        newRecommendation.setSongId(songId);
        newRecommendation.setUserId(userId);
        newRecommendation.setRecommendationDate(recommendationDate);

        RangeBasedRecommendation savedRecommendation = rangeBasedRecommendationRepository.save(newRecommendation);
        return savedRecommendation.getRecommendationId();
    }

    public List<ResRangeBasedRecommendationDto> getVocalRangeRecommendationsByUserId(String userId, int vocalRangeHigh, int vocalRangeLow) {
        // 특정 사용자의 음역대 추천 목록 조회

        List<RangeBasedRecommendation> recommendations = rangeBasedRecommendationRepository.findByUserId(userId);

        List<ResRangeBasedRecommendationDto> recommendationsWithDetails = new ArrayList<>();

        for (RangeBasedRecommendation recommendation : recommendations) {
            Song song = songRepository.findById(recommendation.getSongId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
            recommendationsWithDetails.add(new ResRangeBasedRecommendationDto(recommendation, song));
        }

        return recommendationsWithDetails;
    }

}

