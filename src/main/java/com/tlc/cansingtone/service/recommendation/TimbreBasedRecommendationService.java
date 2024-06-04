package com.tlc.cansingtone.service.recommendation;


import com.tlc.cansingtone.domain.recommendation.TimbreBasedRecommendation;
import com.tlc.cansingtone.domain.Song;
import com.tlc.cansingtone.dto.recommendation.ResRecommendationDto;
import com.tlc.cansingtone.dto.recommendation.ResTimbreBasedRecommendationDto;
import com.tlc.cansingtone.repository.recommendation.TimbreBasedRecommendationRepository;
import com.tlc.cansingtone.repository.SongRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimbreBasedRecommendationService {
    private final TimbreBasedRecommendationRepository timbreBasedRecommendationRepository;
    private final SongRepository songRepository;

    public TimbreBasedRecommendationService(TimbreBasedRecommendationRepository timbreBasedRecommendationRepository, SongRepository songRepository) {
        this.timbreBasedRecommendationRepository = timbreBasedRecommendationRepository;
        this.songRepository = songRepository;
    }

    public Long createNewRecommendation(Long songId, String userId, String recommendationDate, Long timbreId) {
        TimbreBasedRecommendation newRecommendation = new TimbreBasedRecommendation();
        newRecommendation.setSongId(songId);
        newRecommendation.setUserId(userId);
        newRecommendation.setRecommendationDate(recommendationDate);
        newRecommendation.setTimbreId(timbreId);

        TimbreBasedRecommendation savedRecommendation = timbreBasedRecommendationRepository.save(newRecommendation);
        return savedRecommendation.getRecommendationId();
    }


    public List<ResTimbreBasedRecommendationDto> getTimbreRecommendationsByUserId(String userId) {
        // 특정 사용자의 음색 추천 목록 조회

        List<TimbreBasedRecommendation> recommendations = timbreBasedRecommendationRepository.findByUserId(userId);

        List<ResTimbreBasedRecommendationDto> recommendationsWithDetails = new ArrayList<>();

        for (TimbreBasedRecommendation recommendation : recommendations) {
            Song song = songRepository.findById(recommendation.getSongId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
            recommendationsWithDetails.add(new ResTimbreBasedRecommendationDto(recommendation, song));
        }

        return recommendationsWithDetails;
    }


}
