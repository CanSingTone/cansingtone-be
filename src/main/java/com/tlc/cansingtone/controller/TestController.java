package com.tlc.cansingtone.controller;

import com.tlc.cansingtone.BaseResponse;
import com.tlc.cansingtone.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RestController
@RequestMapping(value = "/test", produces = "application/json;charset=utf8")
@Tag(name = "테스트 API")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @Operation(summary = "음역대 테스트용 음성 데이터 전송")
    @PostMapping("/vocal-range")
    public BaseResponse<String> sendPitchVoiceDataToAIServer(@RequestParam(name = "user_id") String userId, @RequestParam(name = "voice_data") MultipartFile file) {
        try {
            String result = testService.sendPitchVoiceDataToAIServer(userId, file);
            return new BaseResponse<>(result);
        } catch (IOException e) {
            return new BaseResponse<>(e.getMessage());
        }
    }

    @Operation(summary = "음색 테스트용 음성 데이터 전송")
    @PostMapping("/timbre")
    public BaseResponse<String> sendTimbreVoiceDataToAIServer(@RequestParam(name = "user_id") String userId, @RequestParam(name = "voice_data") MultipartFile file) {
        try {
            String result = testService.sendTimbreVoiceDataToAIServer(userId, file);
            return new BaseResponse<>(result);
        } catch (IOException e) {
            return new BaseResponse<>(e.getMessage());
        }
    }

}
