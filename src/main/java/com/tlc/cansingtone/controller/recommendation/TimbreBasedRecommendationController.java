package com.tlc.cansingtone.controller.recommendation;

import com.tlc.cansingtone.dto.recommendation.ResTimbreBasedRecommendationDto;
import com.tlc.cansingtone.service.recommendation.TimbreBasedRecommendationService;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.BaseResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RestController
@RequestMapping(value = "/timbre-based-recommendations", produces = "application/json;charset=utf8")
@Tag(name = "음색 기반 추천 API")
public class TimbreBasedRecommendationController {
    private final TimbreBasedRecommendationService timbreBasedRecommendationService;

    public TimbreBasedRecommendationController(TimbreBasedRecommendationService timbreBasedRecommendationService) {
        this.timbreBasedRecommendationService = timbreBasedRecommendationService;
    }

    @Operation(summary = "음색 기반 추천곡 등록")
    @PostMapping
    public BaseResponse<Long> createNewRecommendation(@RequestParam(name = "song_ids") List<Long> songIds,
                                                      @RequestParam(name = "user_id") String userId,
                                                      @RequestParam(name = "recommendation_date") String recommendationDate,
                                                      @RequestParam(name = "timbre_id") Long timbreId) {
        try {
            return new BaseResponse<>(timbreBasedRecommendationService.createNewRecommendation(songIds, userId, recommendationDate, timbreId));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "음색 기반 추천곡 생성 요청")
    @PostMapping("/request")
    public BaseResponse<String> requestTimbreRecommendation(@RequestParam(name = "user_id") String userId,
                                                            @RequestParam(name = "timbre_id") Long timbreId) {
        try {
            return new BaseResponse<>(timbreBasedRecommendationService.requestTimbreRecommendation(userId, timbreId));
        } catch (IOException e) {
            return new BaseResponse<>(e.getMessage());
        }
    }

    @Operation(summary = "특정 사용자의 특정 음색 기반 추천곡 목록")
    @GetMapping("/{userId}")
    public BaseResponse<List<ResTimbreBasedRecommendationDto>> getTimbreRecommendationsByUserId(@PathVariable String userId, @RequestParam(name = "timbre_id") Long timbreId) {

        try {
            List<ResTimbreBasedRecommendationDto> recommendationList = timbreBasedRecommendationService.getTimbreRecommendationsByUserIdAndTimbreId(userId, timbreId);

            return new BaseResponse<>(recommendationList);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

}
