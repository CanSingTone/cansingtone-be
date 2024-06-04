package com.tlc.cansingtone.controller;

import com.tlc.cansingtone.domain.Timbre;
import com.tlc.cansingtone.dto.timbre.ResTimbreDto;
import com.tlc.cansingtone.service.TimbreService;
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
@RequestMapping(value = "/timbre", produces = "application/json;charset=utf8")
@Tag(name = "음색 API")
public class TimbreController {
    private final TimbreService timbreService;

    public TimbreController(TimbreService timbreService) {
        this.timbreService = timbreService;
    }

    @Operation(summary = "음색 정보 등록")
    @PostMapping
    public BaseResponse<ResTimbreDto> createTimbre(
            @RequestParam(name = "timbre_url") String timbreUrl,
            @RequestParam(name = "user_id") String userId) {
        try {
            return new BaseResponse<>(timbreService.createTimbre(timbreUrl, userId));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "음색 ID로 음색 정보 조회")
    @GetMapping("/{timbreId}")
    public BaseResponse<ResTimbreDto> getTimbreByTimbreId(@PathVariable Long timbreId) {
        try {
            return new BaseResponse<>(timbreService.getTimbreByTimbreId(timbreId));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "특정 사용자의 음색 정보 조회")
    @GetMapping
    public BaseResponse<List<ResTimbreDto>> getTimbreByUserId(@RequestParam(name = "user_id") String userId) {
        try {
            List<Timbre> timbres = timbreService.getTimbreByUserId(userId);
            List<ResTimbreDto> responseTimbres = timbres.stream()
                    .map(ResTimbreDto::new)
                    .collect(Collectors.toList());
            return new BaseResponse<>(responseTimbres);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "음색 이름 수정")
    @PatchMapping("/{timbreId}")
    public BaseResponse<ResTimbreDto> updateTimbreName(@PathVariable Long timbreId,
                                                       @RequestParam(name = "timbre_name") String timbreName) {
        try {
            return new BaseResponse<>(timbreService.updateTimbreName(timbreId, timbreName));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "음색 정보 삭제")
    @DeleteMapping("/{timbreId}")
    public BaseResponse<Long> deleteTimbre(@PathVariable Long timbreId) {
        try {
            timbreService.deleteTimbre(timbreId);
            return new BaseResponse<>(timbreId);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

}
