package com.tlc.cansingtone.service.recommendation;

import com.tlc.cansingtone.domain.recommendation.CombinedRecommendation;
import com.tlc.cansingtone.domain.Song;

import com.tlc.cansingtone.dto.recommendation.ResCombinedRecommendationDto;
import com.tlc.cansingtone.repository.recommendation.CombinedRecommendationRepository;
import com.tlc.cansingtone.repository.SongRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
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

    public String requestCombinedRecommendation(String userId) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("user_id", userId);

        // 요청 엔티티 설정
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // API 서버로 POST 요청 보내기
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://3.35.45.229:5000/recommendation-combined",
                requestEntity,
                String.class
        );

        return response.getBody();
    }

    public List<ResCombinedRecommendationDto> getCombinedRecommendationsByUserId(String userId) {
        // 특정 사용자의 종합 추천 목록 조회
        List<CombinedRecommendation> recommendations = combinedRecommendationRepository.findByUserIdOrderByRecommendationDateDesc(userId);

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
