package com.tlc.cansingtone.service;

import com.tlc.cansingtone.domain.Playlist;
import com.tlc.cansingtone.dto.playlist.ResPlaylistDto;
import com.tlc.cansingtone.repository.PlaylistRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
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

    public Playlist updatePlaylistPublic(Long playlistId, int isPublic) {
        Playlist playlist = playlistRepository.findByPlaylistId(playlistId).orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
        playlist.setIsPublic(isPublic);
        return playlistRepository.save(playlist);
    }

}
