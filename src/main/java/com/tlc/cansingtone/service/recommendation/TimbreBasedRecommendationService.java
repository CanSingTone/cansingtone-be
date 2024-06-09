package com.tlc.cansingtone.service.recommendation;


import com.tlc.cansingtone.domain.recommendation.TimbreBasedRecommendation;
import com.tlc.cansingtone.domain.Song;
import com.tlc.cansingtone.dto.recommendation.ResTimbreBasedRecommendationDto;
import com.tlc.cansingtone.repository.recommendation.TimbreBasedRecommendationRepository;
import com.tlc.cansingtone.repository.SongRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimbreBasedRecommendationService {
    private final TimbreBasedRecommendationRepository timbreBasedRecommendationRepository;
    private final SongRepository songRepository;

    public TimbreBasedRecommendationService(TimbreBasedRecommendationRepository timbreBasedRecommendationRepository, SongRepository songRepository) {
        this.timbreBasedRecommendationRepository = timbreBasedRecommendationRepository;
        this.songRepository = songRepository;
    }

    public Long createNewRecommendation(List<Long> songIds, String userId, String recommendationDate, Long timbreId) {
        // 곡 ID 리스트를 쉼표로 구분된 문자열로 변환하여 저장
        String songIdsAsString = songIds.stream().map(Object::toString).collect(Collectors.joining(","));

        TimbreBasedRecommendation newRecommendation = new TimbreBasedRecommendation();
        newRecommendation.setSongIds(songIdsAsString);
        newRecommendation.setUserId(userId);
        newRecommendation.setRecommendationDate(recommendationDate);
        newRecommendation.setTimbreId(timbreId);

        TimbreBasedRecommendation savedRecommendation = timbreBasedRecommendationRepository.save(newRecommendation);
        return savedRecommendation.getRecommendationId();
    }

    public String requestTimbreRecommendation(String userId, Long timbreId) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("user_id", userId);
        body.put("timbre_id", timbreId);

        // 요청 엔티티 설정
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // API 서버로 POST 요청 보내기
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://13.209.221.128:5000/recommendation-timbre",
                requestEntity,
                String.class
        );

        return response.getBody();
    }


    public List<ResTimbreBasedRecommendationDto> getTimbreRecommendationsByUserIdAndTimbreId(String userId, Long timbreId) {
        // 특정 사용자의 음색 추천 목록 조회
        List<TimbreBasedRecommendation> recommendations = timbreBasedRecommendationRepository.findByUserIdAndTimbreIdOrderByRecommendationDateDesc(userId, timbreId);

        List<ResTimbreBasedRecommendationDto> recommendationsWithDetails = new ArrayList<>();

        for (TimbreBasedRecommendation recommendation : recommendations) {
            String songIdsAsString = recommendation.getSongIds();
            List<Long> songIds = Arrays.stream(songIdsAsString.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            for (Long songId : songIds) {
                Song song = songRepository.findById(songId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
                recommendationsWithDetails.add(new ResTimbreBasedRecommendationDto(recommendation, song));
            }
        }

        return recommendationsWithDetails;
    }


}
