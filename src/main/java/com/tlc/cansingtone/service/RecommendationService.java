package com.tlc.cansingtone.service;

import com.tlc.cansingtone.domain.Recommendation;
import com.tlc.cansingtone.domain.Song;
import com.tlc.cansingtone.domain.SongInPlaylist;
import com.tlc.cansingtone.dto.recommendation.ResRecommendationDto;
import com.tlc.cansingtone.dto.song_in_playlist.ResSongInPlaylistDto;
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

    public Long createNewRecommendation(Long songId, String userId, String recommendationMethod, String recommendationDate) {
        Recommendation newRecommendation = new Recommendation();
        newRecommendation.setSongId(songId);
        newRecommendation.setUserId(userId);
        newRecommendation.setRecommendationMethod(recommendationMethod);
        newRecommendation.setRecommendationDate(recommendationDate);

        Recommendation savedRecommendation = recommendationRepository.save(newRecommendation);
        return savedRecommendation.getRecommendationId();
    }

    public List<ResRecommendationDto> getRecommendationsByUserId(String userId) {
        // 특정 사용자의 추천 목록 조회

        List<Recommendation> recommendations = recommendationRepository.findByUserId(userId);
        List<ResRecommendationDto> recommendationsWithDetails = new ArrayList<>();

        for (Recommendation recommendation : recommendations) {
            Song song = songRepository.findById(recommendation.getSongId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
            recommendationsWithDetails.add(new ResRecommendationDto(recommendation, song));
        }

        return recommendationsWithDetails;
    }

}
