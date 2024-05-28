package com.tlc.cansingtone.controller;

import com.tlc.cansingtone.domain.Song;
import com.tlc.cansingtone.service.RecommendationService;
import com.tlc.cansingtone.dto.recommendation.ResRecommendationDto;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.BaseResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RestController
@RequestMapping(value = "/recommendations", produces = "application/json;charset=utf8")
@Tag(name = "추천 API")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Operation(summary = "추천곡 등록")
    @PostMapping
    public BaseResponse<Long> createNewRecommendation(@RequestParam(name = "song_id") Long songId,
                                                      @RequestParam(name = "user_id") String userId,
                                                      @RequestParam(name = "recommendation_method") int recommendationMethod,
                                                      @RequestParam(name = "recommendation_date") String recommendationDate) {
        try {
            return new BaseResponse<>(recommendationService.createNewRecommendation(songId, userId, recommendationMethod, recommendationDate));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "특정 사용자의 음역대 기반 추천 목록")
    @GetMapping("/{userId}/vocal-range")
    public BaseResponse<List<Song>> getVocalRangeRecommendationsByUserId(@PathVariable String userId, @RequestParam(name = "vocal_range_high") int vocalRangeHigh, @RequestParam(name = "vocal_range_low") int vocalRangeLow) {
        try {
            List<Song> recommendationList = recommendationService.getVocalRangeRecommendationsByUserId(userId, vocalRangeHigh, vocalRangeLow);
            return new BaseResponse<>(recommendationList);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "특정 사용자의 음색 기반 추천 목록")
    @GetMapping("/{userId}/timbre")
    public BaseResponse<List<ResRecommendationDto>> getTimbreRecommendationsByUserId(@PathVariable String userId) {
        try {
            List<ResRecommendationDto> recommendationList = recommendationService.getTimbreRecommendationsByUserId(userId);
            return new BaseResponse<>(recommendationList);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "특정 사용자의 종합 추천 목록")
    @GetMapping("/{userId}/combined")
    public BaseResponse<List<ResRecommendationDto>> getCombinedRecommendationsByUserId(@PathVariable String userId) {
        try {
            List<ResRecommendationDto> recommendationList = recommendationService.getCombinedRecommendationsByUserId(userId);
            return new BaseResponse<>(recommendationList);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }


}
