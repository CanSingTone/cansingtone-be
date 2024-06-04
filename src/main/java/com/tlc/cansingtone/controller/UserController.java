package com.tlc.cansingtone.controller;

import com.tlc.cansingtone.domain.User;
import com.tlc.cansingtone.service.UserService;
import com.tlc.cansingtone.dto.user.ResUserDto;
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
@RequestMapping(value = "/users", produces = "application/json;charset=utf8")
@Tag(name = "회원 API")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "새로운 회원인 경우 회원 생성")
    @PostMapping
    public BaseResponse<ResUserDto> createNewUser(
            @RequestParam(name = "user_id") String user_id,
            @RequestParam(name = "nickname") String nickname,
            @RequestParam(name = "gender") int gender,
            @RequestParam(name = "ages") int ages,
            @RequestParam(name = "pref_genre1") int pref_genre1,
            @RequestParam(name = "pref_genre2") int pref_genre2,
            @RequestParam(name = "pref_genre3") int pref_genre3) {
        try {
            String userId = userService.createNewUser(user_id, nickname, gender, ages, pref_genre1, pref_genre2, pref_genre3);
            ResUserDto createdUser = userService.getUserOne(userId);
            return new BaseResponse<>(createdUser);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "아이디로 회원 존재 여부 확인")
    @GetMapping("/exists")
    public BaseResponse<Boolean> checkUserByUserId(@RequestParam(name = "user_id") String userId) {
        try {
            boolean userExists = userService.checkUserByUserId(userId);
            return new BaseResponse<>(userExists);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "회원 전체 목록")
    @GetMapping
    public BaseResponse<List<ResUserDto>> getUsers() {
        try {
            List<User> users = userService.getUsers();
            List<ResUserDto> userList = users.stream()
                    .map(ResUserDto::new)
                    .collect(Collectors.toList());
            return new BaseResponse<>(userList);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "회원 ID로 정보 조회")
    @GetMapping("/{userId}")
    public BaseResponse<ResUserDto> getUser(@PathVariable String userId) {
        try {
            return new BaseResponse<>(userService.getUserOne(userId));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "회원 정보 수정")
    @PatchMapping("/{userId}")
    public BaseResponse<ResUserDto> patchUser(
            @PathVariable String userId,
            @RequestParam(name = "nickname", required = false) String nickname,
            @RequestParam(name = "ages", required = false) Integer ages,
            @RequestParam(name = "pref_genre1", required = false) Integer pref_genre1,
            @RequestParam(name = "pref_genre2", required = false) Integer pref_genre2,
            @RequestParam(name = "pref_genre3", required = false) Integer pref_genre3) {
        try {
            User updatedUser = userService.patchUser(userId, nickname, ages, pref_genre1, pref_genre2, pref_genre3);
            return new BaseResponse<>(new ResUserDto(updatedUser));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }


    // AI에서 사용하는 API

    @Operation(summary = "회원 음역대 정보 등록 및 수정")
    @PatchMapping("/{userId}/vocal-range")
    public BaseResponse<ResUserDto> patchVocalRange(
            @PathVariable String userId,
            @RequestParam(name = "vocal_range_high") int vocalRangeHigh,
            @RequestParam(name = "vocal_range_low") int vocalRangeLow) {
        try {
            User updatedUser = userService.patchVocalRange(userId, vocalRangeHigh, vocalRangeLow);
            return new BaseResponse<>(new ResUserDto(updatedUser));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

//    @Operation(summary = "회원 음색 정보 등록 및 수정")
//    @PatchMapping("/{userId}/timbre")
//    public BaseResponse<ResUserDto> updateVocalRange(
//            @PathVariable String userId,
//            @RequestParam(name = "timbre_url") String timbreUrl) {
//        try {
//            User updatedUser = userService.updateTimbre(userId, timbreUrl);
//            return new BaseResponse<>(new ResUserDto(updatedUser));
//        } catch (BusinessException e) {
//            return new BaseResponse<>(e.getErrorCode());
//        }
//    }

}

