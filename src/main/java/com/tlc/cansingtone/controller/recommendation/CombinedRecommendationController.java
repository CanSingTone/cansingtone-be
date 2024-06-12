package com.tlc.cansingtone.controller.recommendation;

import com.tlc.cansingtone.dto.recommendation.ResCombinedRecommendationDto;
import com.tlc.cansingtone.service.recommendation.CombinedRecommendationService;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.BaseResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RestController
@RequestMapping(value = "/combined-recommendations", produces = "application/json;charset=utf8")
@Tag(name = "종합 추천 API")
public class CombinedRecommendationController {
    private final CombinedRecommendationService combinedRecommendationService;

    public CombinedRecommendationController(CombinedRecommendationService combinedRecommendationService) {
        this.combinedRecommendationService = combinedRecommendationService;
    }

    @Operation(summary = "추천곡 등록")
    @PostMapping
    public BaseResponse<Long> createNewRecommendation(@RequestParam(name = "song_ids") List<Long> songIds,
                                                      @RequestParam(name = "user_id") String userId,
                                                      @RequestParam(name = "recommendation_date") String recommendationDate) {
        try {
            return new BaseResponse<>(combinedRecommendationService.createNewRecommendation(songIds, userId, recommendationDate));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "종합 추천곡 생성 요청")
    @PostMapping("/request")
    public BaseResponse<String> requestTimbreRecommendation(@RequestParam(name = "user_id") String userId) {
        try {
            return new BaseResponse<>(combinedRecommendationService.requestCombinedRecommendation(userId));
        } catch (IOException e) {
            return new BaseResponse<>(e.getMessage());
        }
    }

    @Operation(summary = "특정 사용자의 종합 추천 목록")
    @GetMapping("/{userId}")
    public BaseResponse<List<ResCombinedRecommendationDto>> getCombinedRecommendationsByUserId(@PathVariable String userId) {
        try {
            List<ResCombinedRecommendationDto> recommendationList = combinedRecommendationService.getCombinedRecommendationsByUserId(userId);
            return new BaseResponse<>(recommendationList);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

}
