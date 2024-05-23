package com.tlc.cansingtone.controller;

import com.tlc.cansingtone.dto.song.ResSongDto;
import com.tlc.cansingtone.service.SongService;

import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.BaseResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RestController
@RequestMapping(value = "/songs", produces = "application/json;charset=utf8")
@Tag(name = "노래 API")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @Operation(summary = "곡 ID로 곡 정보 조회")
    @GetMapping("/{songId}")
    public BaseResponse<ResSongDto> getUser(@PathVariable Long songId) {
        try {
            return new BaseResponse<>(songService.getSongbySongId(songId));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }


}
