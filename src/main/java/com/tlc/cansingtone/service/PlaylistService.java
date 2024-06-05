package com.tlc.cansingtone.service;

import com.tlc.cansingtone.domain.Playlist;
import com.tlc.cansingtone.domain.User;
import com.tlc.cansingtone.dto.playlist.ResPlaylistDto;
import com.tlc.cansingtone.repository.PlaylistRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final UserService userService;

    public PlaylistService(PlaylistRepository playlistRepository, UserService userService) {
        this.playlistRepository = playlistRepository;
        this.userService = userService;
    }

    public Long createNewPlaylist(String userId, String playlistName, int isPublic) {
        Playlist newPlaylist = new Playlist();
        newPlaylist.setUserId(userId);
        newPlaylist.setPlaylistName(playlistName);
        newPlaylist.setIsPublic(isPublic);

        Playlist savedPlaylist = playlistRepository.save(newPlaylist);
        return savedPlaylist.getPlaylistId();
    }

    public List<Playlist> getPlaylists() {
        List<Playlist> playlists = playlistRepository.findAll();
        return playlists;
    }


    public ResPlaylistDto getPlaylistOne(Long playlistId) {
        Playlist playlist = playlistRepository.findByPlaylistId(playlistId).orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
        ResPlaylistDto response = new ResPlaylistDto(playlist);
        return response;
    }


    public List<Playlist> getPlaylistsByUserId(String userId) {
        // 특정 사용자의 플레이리스트 목록 조회
        List<Playlist> playlists = playlistRepository.findByUserId(userId);
        return playlists;
    }


    public List<Playlist> getPlaylistsBySimilarVocalRange(String userId, int vocalRangeHigh, int vocalRangeLow) {
        // 음역대가 비슷한 유저를 찾습니다.
        List<User> similarUsers = userService.findUsersWithSimilarVocalRange(userId, vocalRangeHigh, vocalRangeLow);

        // 비슷한 유저들의 플레이리스트를 가져옵니다.
        List<Playlist> playlists = new ArrayList<>();
        for (User user : similarUsers) {
            List<Playlist> userPlaylists = playlistRepository.findByUserId(user.getUserId());
            // is_public이 true인 플레이리스트만 필터링합니다.
            List<Playlist> publicPlaylists = userPlaylists.stream()
                    .filter(playlist -> playlist.getIsPublic() == 1)
                    .collect(Collectors.toList());
            playlists.addAll(publicPlaylists);
        }

        return playlists;
    }

    public Playlist updatePlaylistPublic(Long playlistId, int isPublic) {
        Playlist playlist = playlistRepository.findByPlaylistId(playlistId).orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
        playlist.setIsPublic(isPublic);
        return playlistRepository.save(playlist);
    }

}
