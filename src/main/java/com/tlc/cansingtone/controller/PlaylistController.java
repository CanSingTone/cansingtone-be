package com.tlc.cansingtone.controller;

import com.tlc.cansingtone.domain.Playlist;
import com.tlc.cansingtone.service.PlaylistService;
import com.tlc.cansingtone.dto.playlist.ResPlaylistDto;
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
@RequestMapping(value = "/playlists", produces = "application/json;charset=utf8")
@Tag(name = "플레이리스트 API")
public class PlaylistController {
    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Operation(summary = "플레이리스트 생성")
    @PostMapping
    public BaseResponse<Long> createNewPlaylist(@RequestParam(name = "user_id") String userId,
                                                @RequestParam(name = "playlist_name") String playlistName,
                                                @RequestParam(name = "is_public") int isPublic) {
        try {
            return new BaseResponse<>(playlistService.createNewPlaylist(userId, playlistName, isPublic));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "전체 플레이리스트 목록")
    @GetMapping
    public BaseResponse<List<ResPlaylistDto>> getPlaylists() {
        try {
            List<Playlist> playlists = playlistService.getPlaylists();
            List<ResPlaylistDto> playlistList = playlists.stream()
                    .map(ResPlaylistDto::new)
                    .collect(Collectors.toList());
            return new BaseResponse<>(playlistList);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "특정 사용자의 플레이리스트 목록")
    @GetMapping("/{userId}")
    public BaseResponse<List<ResPlaylistDto>> getPlaylistsByUserId(@PathVariable String userId) {
        try {
            List<Playlist> playlists = playlistService.getPlaylistsByUserId(userId);
            List<ResPlaylistDto> playlistList = playlists.stream()
                    .map(ResPlaylistDto::new)
                    .collect(Collectors.toList());
            return new BaseResponse<>(playlistList);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "나와 음역대가 비슷한 사용자의 플레이리스트 목록")
    @GetMapping("/similar-vocal-range")
    public BaseResponse<List<ResPlaylistDto>> getPlaylistsBySimilarVocalRange(@RequestParam(name = "user_id") String userId,
                                                                              @RequestParam(name = "vocal_range_high") int vocalRangeHigh,
                                                                              @RequestParam(name = "vocal_range_low") int vocalRangeLow) {
        try {
            List<Playlist> playlists = playlistService.getPlaylistsBySimilarVocalRange(userId, vocalRangeHigh, vocalRangeLow);
            List<ResPlaylistDto> playlistList = playlists.stream()
                    .map(ResPlaylistDto::new)
                    .collect(Collectors.toList());
            return new BaseResponse<>(playlistList);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }


    @Operation(summary = "플레이리스트 공개 여부 수정")
    @PatchMapping("/{playlistId}/is-public")
    public BaseResponse<ResPlaylistDto> updatePlaylistPublic(@PathVariable Long playlistId,
                                                             @RequestParam(name = "is_public") int isPublic) {
        try {
            Playlist updatedPlaylist = playlistService.updatePlaylistPublic(playlistId, isPublic);
            return new BaseResponse<>(new ResPlaylistDto(updatedPlaylist));
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

//    @Operation(summary = "플레이리스트 정보 조회")
//    @GetMapping("/{playlistId}")
//    public BaseResponse<PlaylistResponseDto> getPlaylist(@PathVariable Long playlistId) {
//        try {
//            return new BaseResponse<>(playlistService.getPlaylistOne(playlistId));
//        } catch (BusinessException e) {
//            return new BaseResponse<>(e.getErrorCode());
//        }
//    }
}
