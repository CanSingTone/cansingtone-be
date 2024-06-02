package com.tlc.cansingtone.service;

import com.tlc.cansingtone.domain.Recommendation;
import com.tlc.cansingtone.domain.Song;
import com.tlc.cansingtone.dto.recommendation.ResRecommendationDto;
import com.tlc.cansingtone.repository.RecommendationRepository;
import com.tlc.cansingtone.repository.SongRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final SongRepository songRepository;

    public RecommendationService(RecommendationRepository recommendationRepository, SongRepository songRepository) {
        this.recommendationRepository = recommendationRepository;
        this.songRepository = songRepository;
    }

    public Long createNewRecommendation(Long songId, String userId, int recommendationMethod, String recommendationDate) {
        Recommendation newRecommendation = new Recommendation();
        newRecommendation.setSongId(songId);
        newRecommendation.setUserId(userId);
        newRecommendation.setRecommendationMethod(recommendationMethod);
        newRecommendation.setRecommendationDate(recommendationDate);

        Recommendation savedRecommendation = recommendationRepository.save(newRecommendation);
        return savedRecommendation.getRecommendationId();
    }

    public List<Song> getVocalRangeRecommendationsByUserId(String userId, int vocalRangeHigh, int vocalRangeLow) {
        // 특정 사용자의 음역대 추천 목록 조회

        List<Song> songs = songRepository.findByHighestNoteGreaterThanEqualAndLowestNoteLessThanEqual(vocalRangeHigh, vocalRangeLow);
        return songs;
    }

    public List<ResRecommendationDto> getTimbreRecommendationsByUserId(String userId) {
        // 특정 사용자의 음색 추천 목록 조회

        List<Recommendation> recommendations = recommendationRepository.findByUserIdAndRecommendationMethod(userId, 2);

        List<ResRecommendationDto> recommendationsWithDetails = new ArrayList<>();

        for (Recommendation recommendation : recommendations) {
            Song song = songRepository.findById(recommendation.getSongId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
            recommendationsWithDetails.add(new ResRecommendationDto(recommendation, song));
        }

        return recommendationsWithDetails;
    }

    public List<ResRecommendationDto> getCombinedRecommendationsByUserId(String userId) {
        // 특정 사용자의 음색 추천 목록 조회

        List<Recommendation> recommendations = recommendationRepository.findByUserIdAndRecommendationMethod(userId, 3);
        List<ResRecommendationDto> recommendationsWithDetails = new ArrayList<>();

        for (Recommendation recommendation : recommendations) {
            Song song = songRepository.findById(recommendation.getSongId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
            recommendationsWithDetails.add(new ResRecommendationDto(recommendation, song));
        }

        return recommendationsWithDetails;
    }

}
