package com.tlc.cansingtone.controller.chart;

import com.tlc.cansingtone.domain.chart.KaraokeTopChart;

import com.tlc.cansingtone.service.chart.KaraokeTopChartService;
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
@RequestMapping(value = "/charts", produces = "application/json;charset=utf8")
@Tag(name = "노래방 TOP API")
public class KaraokeTopChartController {
    private final KaraokeTopChartService karaokeTopChartService;

    public KaraokeTopChartController(KaraokeTopChartService karaokeTopChartService) {
        this.karaokeTopChartService = karaokeTopChartService;
    }

    @Operation(summary = "노래방 TOP 차트 조회")
    @GetMapping("/karaoke-top-chart")
    public BaseResponse<List<KaraokeTopChart>> getKaraokeTopChart() {
        try {
            return new BaseResponse<>(karaokeTopChartService.getKaraokeTopChart());
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }
}
