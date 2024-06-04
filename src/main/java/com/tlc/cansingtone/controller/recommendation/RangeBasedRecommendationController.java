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

    @Operation(summary = "추천곡 등록")
    @PostMapping
    public BaseResponse<Long> createNewRecommendation(@RequestParam(name = "song_id") Long songId,
                                                      @RequestParam(name = "user_id") String userId,

                                                      @RequestParam(name = "recommendation_date") String recommendationDate) {
        try {
            return new BaseResponse<>(rangeBasedRecommendationService.createNewRecommendation(songId, userId, recommendationDate));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "특정 사용자의 음역대 기반 추천 목록")
    @GetMapping("/{userId}")
    public BaseResponse<List<ResRangeBasedRecommendationDto>> getVocalRangeRecommendationsByUserId(@PathVariable String userId, @RequestParam(name = "vocal_range_high") int vocalRangeHigh, @RequestParam(name = "vocal_range_low") int vocalRangeLow) {
        try {
            List<ResRangeBasedRecommendationDto> recommendationList = rangeBasedRecommendationService.getVocalRangeRecommendationsByUserId(userId, vocalRangeHigh, vocalRangeLow);
            return new BaseResponse<>(recommendationList);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

}
