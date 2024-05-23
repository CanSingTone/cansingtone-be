package com.tlc.cansingtone.controller;

import com.tlc.cansingtone.dto.like.ReqLikeDto;
import com.tlc.cansingtone.dto.like.ResLikeDto;
import com.tlc.cansingtone.dto.like.ResSongInLikeListDto;
import com.tlc.cansingtone.service.LikeService;

import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.BaseResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Controller
@RestController
@RequestMapping(value = "/like", produces = "application/json;charset=utf8")
@Tag(name = "좋아요 표시한 곡 API")

public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @Operation(summary = "좋아요 정보 생성")
    @PostMapping
    public BaseResponse<Long> createLike(@RequestParam(name = "user_id") String userId, @RequestParam(name = "song_id") Long songId) {
        try {
            Long likeId = likeService.createLike(userId, songId);
            return new BaseResponse<>(likeId);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "사용자 ID로 좋아요 표시한 곡 목록 조회")
    @GetMapping("/{userId}")
    public BaseResponse<List<ResSongInLikeListDto>> getLike(@PathVariable String userId) {
        try {
            return new BaseResponse<>(likeService.getLikedSongsByUserId(userId));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

}
