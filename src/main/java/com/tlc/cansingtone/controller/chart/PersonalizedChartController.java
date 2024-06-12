package com.tlc.cansingtone.controller.chart;

import com.tlc.cansingtone.domain.chart.PersonalizedChart;

import com.tlc.cansingtone.service.chart.PersonalizedChartService;
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
@Tag(name = "나이 및 성별 선호 차트 API")
public class PersonalizedChartController {
    private final PersonalizedChartService personalizedChartService;

    public PersonalizedChartController(PersonalizedChartService personalizedChartService) {
        this.personalizedChartService = personalizedChartService;
    }

    @Operation(summary = "나이 및 성별 선호 차트 조회")
    @GetMapping("/personalized-chart")
    public BaseResponse<List<PersonalizedChart>> getPersonalizedChart(@RequestParam(name = "age") int age, @RequestParam(name = "gender") int gender) {
        try {
            return new BaseResponse<>(personalizedChartService.getPersonalizedChart(age, gender));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }


}
