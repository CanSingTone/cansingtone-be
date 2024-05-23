package com.tlc.cansingtone.controller;

import com.tlc.cansingtone.domain.Song;
import com.tlc.cansingtone.dto.song.ResSongDto;
import com.tlc.cansingtone.service.SongService;
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
@RequestMapping(value = "/songs", produces = "application/json;charset=utf8")
@Tag(name = "노래 API")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @Operation(summary = "곡 ID로 곡 정보 조회")
    @GetMapping("/{songId}")
    public BaseResponse<ResSongDto> getSongbySongId(@PathVariable Long songId) {
        try {
            return new BaseResponse<>(songService.getSongbySongId(songId));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "곡 제목 및 가수 이름으로 곡 검색")
    @GetMapping("/search")
    public BaseResponse<List<ResSongDto>> getSongsbyTitleOrArtist(@RequestParam String keyword) {
        try {
            List<Song> songs = songService.getSongsbyTitleOrArtist(keyword);
            // Song을 ResSongDto로 변환하여 리스트로 만듭니다.
            List<ResSongDto> responseSongs = songs.stream()
                    .map(ResSongDto::new)
                    .collect(Collectors.toList());
            return new BaseResponse<>(responseSongs);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "장르 및 음역대로 곡 필터링")
    @GetMapping("/filter")
    public BaseResponse<List<ResSongDto>> getSongsByGenreAndVocalRange(
            @RequestParam(name = "genres", required = false) List<Integer> genres,
            @RequestParam(name = "highest_note", required = false, defaultValue = "-1") Integer highestNote,
            @RequestParam(name = "lowest_note", required = false, defaultValue = "-1") Integer lowestNote) {
        try {
            List<Song> songs = songService.getSongsByGenreAndVocalRange(genres, highestNote, lowestNote);
            List<ResSongDto> responseSongs = songs.stream()
                    .map(ResSongDto::new)
                    .collect(Collectors.toList());
            return new BaseResponse<>(responseSongs);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

}
