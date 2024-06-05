package com.tlc.cansingtone.service.recommendation;

import com.tlc.cansingtone.domain.recommendation.CombinedRecommendation;
import com.tlc.cansingtone.domain.Song;

import com.tlc.cansingtone.domain.recommendation.TimbreBasedRecommendation;
import com.tlc.cansingtone.dto.recommendation.ResCombinedRecommendationDto;
import com.tlc.cansingtone.dto.recommendation.ResTimbreBasedRecommendationDto;
import com.tlc.cansingtone.repository.recommendation.CombinedRecommendationRepository;
import com.tlc.cansingtone.repository.SongRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CombinedRecommendationService {
    private final CombinedRecommendationRepository combinedRecommendationRepository;
    private final SongRepository songRepository;

    public CombinedRecommendationService(CombinedRecommendationRepository combinedRecommendationRepository, SongRepository songRepository) {
        this.combinedRecommendationRepository = combinedRecommendationRepository;
        this.songRepository = songRepository;
    }

    public Long createNewRecommendation(List<Long> songIds, String userId, String recommendationDate) {
        // 곡 ID 리스트를 쉼표로 구분된 문자열로 변환하여 저장
        String songIdsAsString = songIds.stream().map(Object::toString).collect(Collectors.joining(","));

        CombinedRecommendation newRecommendation = new CombinedRecommendation();
        newRecommendation.setSongIds(songIdsAsString);
        newRecommendation.setUserId(userId);
        newRecommendation.setRecommendationDate(recommendationDate);

        CombinedRecommendation savedRecommendation = combinedRecommendationRepository.save(newRecommendation);
        return savedRecommendation.getRecommendationId();
    }

    public List<ResCombinedRecommendationDto> getCombinedRecommendationsByUserId(String userId) {
        // 특정 사용자의 종합 추천 목록 조회
        List<CombinedRecommendation> recommendations = combinedRecommendationRepository.findByUserId(userId);

        List<ResCombinedRecommendationDto> recommendationsWithDetails = new ArrayList<>();

        for (CombinedRecommendation recommendation : recommendations) {
            String songIdsAsString = recommendation.getSongIds();
            List<Long> songIds = Arrays.stream(songIdsAsString.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            for (Long songId : songIds) {
                Song song = songRepository.findById(songId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
                recommendationsWithDetails.add(new ResCombinedRecommendationDto(recommendation, song));
            }
        }

        return recommendationsWithDetails;
    }
}
