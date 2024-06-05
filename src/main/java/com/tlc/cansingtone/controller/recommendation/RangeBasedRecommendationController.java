package com.tlc.cansingtone.controller.recommendation;

import com.tlc.cansingtone.dto.recommendation.ResRangeBasedRecommendationDto;
import com.tlc.cansingtone.service.recommendation.RangeBasedRecommendationService;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.BaseResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RestController
@RequestMapping(value = "/range-based-recommendations", produces = "application/json;charset=utf8")
@Tag(name = "음역대 기반 추천 API")
public class RangeBasedRecommendationController {
    private final RangeBasedRecommendationService rangeBasedRecommendationService;

    public RangeBasedRecommendationController(RangeBasedRecommendationService rangeBasedRecommendationService) {
        this.rangeBasedRecommendationService = rangeBasedRecommendationService;
    }

    @Operation(summary = "음역대 기반 추천곡 생성")
    @PostMapping
    public BaseResponse<Long> createNewRecommendation(
            @RequestParam(name = "user_id") String userId,
            @RequestParam(name = "vocal_range_high") int vocalRangeHigh, @RequestParam(name = "vocal_range_low") int vocalRangeLow
    ) {
        try {
            return new BaseResponse<>(rangeBasedRecommendationService.createNewRecommendation(userId, vocalRangeHigh, vocalRangeLow));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "특정 사용자의 음역대 기반 추천 목록")
    @GetMapping("/{userId}")
    public BaseResponse<List<ResRangeBasedRecommendationDto>> getVocalRangeRecommendationsByUserId(@PathVariable String userId) {
        try {
            List<ResRangeBasedRecommendationDto> recommendationList = rangeBasedRecommendationService.getVocalRangeRecommendationsByUserId(userId);
            return new BaseResponse<>(recommendationList);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

}
