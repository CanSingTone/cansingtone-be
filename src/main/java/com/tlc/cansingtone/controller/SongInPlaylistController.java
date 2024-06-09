package com.tlc.cansingtone.controller;

import com.tlc.cansingtone.dto.song_in_playlist.ResSongInPlaylistDto;
import com.tlc.cansingtone.service.SongInPlaylistService;

import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.BaseResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RestController
@RequestMapping(value = "/songs-in-playlist", produces = "application/json;charset=utf8")
@Tag(name = "플레이리스트에 포함된 곡 API")
public class SongInPlaylistController {
    private final SongInPlaylistService songInPlaylistService;

    public SongInPlaylistController(SongInPlaylistService songInPlaylistService) {
        this.songInPlaylistService = songInPlaylistService;
    }

    @Operation(summary = "플레이리스트에 곡 추가")
    @PostMapping
    public BaseResponse<Long> addSongToPlaylist(@RequestParam(name = "playlist_id") Long playlistId,
                                                @RequestParam(name = "song_id") Long songId) {
        try {
            Long songInPlaylistId = songInPlaylistService.addSongToPlaylist(playlistId, songId);
            return new BaseResponse<>(songInPlaylistId);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "플레이리스트에 포함된 곡 목록 조회")
    @GetMapping("/{playlistId}")
    public BaseResponse<List<ResSongInPlaylistDto>> getSongsInPlaylist(@PathVariable Long playlistId) {
        try {
            List<ResSongInPlaylistDto> songsInPlaylist = songInPlaylistService.getSongsInPlaylist(playlistId);

            return new BaseResponse<>(songsInPlaylist);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "플레이리스트에서 곡 삭제")
    @DeleteMapping
    public BaseResponse<Long> deleteSongFromPlaylist(@RequestParam(name = "song_in_playlist_id") Long songInPlaylistId) {
        try {
            songInPlaylistService.deleteSongFromPlaylist(songInPlaylistId);
            return new BaseResponse<>(songInPlaylistId);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }
}

