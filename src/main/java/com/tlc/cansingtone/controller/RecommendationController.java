package com.tlc.cansingtone.controller;

import com.tlc.cansingtone.domain.Recommendation;
import com.tlc.cansingtone.service.RecommendationService;
import com.tlc.cansingtone.dto.recommendation.ResRecommendationDto;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.BaseResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
                                                      @RequestParam(name = "recommendation_method") String recommendationMethod,
                                                      @RequestParam(name = "recommendation_date") String recommendationDate) {
        try {
            return new BaseResponse<>(recommendationService.createNewRecommendation(songId, userId, recommendationMethod, recommendationDate));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "특정 사용자의 추천 목록")
    @GetMapping("/{userId}")
    public BaseResponse<List<ResRecommendationDto>> getRecommendationsByUserId(@PathVariable String userId) {
        try {
            List<ResRecommendationDto> recommendationList = recommendationService.getRecommendationsByUserId(userId);
            return new BaseResponse<>(recommendationList);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

}
