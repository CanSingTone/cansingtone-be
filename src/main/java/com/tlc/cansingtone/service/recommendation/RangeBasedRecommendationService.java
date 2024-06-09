package com.tlc.cansingtone.service.recommendation;

import com.tlc.cansingtone.domain.recommendation.RangeBasedRecommendation;
import com.tlc.cansingtone.domain.Song;

import com.tlc.cansingtone.dto.recommendation.ResRangeBasedRecommendationDto;
import com.tlc.cansingtone.repository.recommendation.RangeBasedRecommendationRepository;
import com.tlc.cansingtone.repository.SongRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RangeBasedRecommendationService {
    private final RangeBasedRecommendationRepository rangeBasedRecommendationRepository;
    private final SongRepository songRepository;

    public RangeBasedRecommendationService(RangeBasedRecommendationRepository rangeBasedRecommendationRepository, SongRepository songRepository) {
        this.rangeBasedRecommendationRepository = rangeBasedRecommendationRepository;
        this.songRepository = songRepository;
    }

    public Long createNewRecommendation(String userId, int vocalRangeHigh, int vocalRangeLow) {
        List<Song> songsInRange = songRepository.findByHighestNoteLessThanEqualAndLowestNoteGreaterThanEqual(vocalRangeHigh, vocalRangeLow);

        if (songsInRange == null || songsInRange.isEmpty()) {
            throw new BusinessException(ErrorCode.NO_EXIST_SONG_IN_RANGE);
        }

        Collections.shuffle(songsInRange);
        List<Long> recommendedSongIds = songsInRange.stream().limit(10).map(Song::getSongId).collect(Collectors.toList());

        // List<Long>을 쉼표로 구분된 문자열로 변환
        String songIdsAsString = recommendedSongIds.stream().map(Object::toString).collect(Collectors.joining(","));

        RangeBasedRecommendation recommendation = new RangeBasedRecommendation();
        recommendation.setUserId(userId);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = now.format(formatter);
        recommendation.setRecommendationDate(formattedDateTime);

        recommendation.setSongIds(songIdsAsString);

        RangeBasedRecommendation savedRecommendation = rangeBasedRecommendationRepository.save(recommendation);
        return savedRecommendation.getRecommendationId();
    }

    public List<ResRangeBasedRecommendationDto> getVocalRangeRecommendationsByUserId(String userId) {
        // 특정 사용자의 음역대 추천 목록 조회
        List<RangeBasedRecommendation> recommendations = rangeBasedRecommendationRepository.findByUserIdOrderByRecommendationDateDesc(userId);
        List<ResRangeBasedRecommendationDto> recommendationsWithDetails = new ArrayList<>();

        for (RangeBasedRecommendation recommendation : recommendations) {
            String songIdsAsString = recommendation.getSongIds();
            List<Long> songIds = Arrays.stream(songIdsAsString.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            for (Long songId : songIds) {
                Song song = songRepository.findById(songId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
                recommendationsWithDetails.add(new ResRangeBasedRecommendationDto(recommendation, song));
            }
        }

        return recommendationsWithDetails;
    }
}

