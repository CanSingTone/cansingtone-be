package com.tlc.cansingtone.service;

import com.tlc.cansingtone.domain.Song;
import com.tlc.cansingtone.domain.SongInPlaylist;
import com.tlc.cansingtone.dto.song_in_playlist.ResSongInPlaylistDto;
import com.tlc.cansingtone.repository.SongRepository;
import com.tlc.cansingtone.repository.SongInPlaylistRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongInPlaylistService {
    private final SongInPlaylistRepository songInPlaylistRepository;
    private final SongRepository songRepository;

    public SongInPlaylistService(SongInPlaylistRepository songInPlaylistRepository, SongRepository songRepository) {
        this.songInPlaylistRepository = songInPlaylistRepository;
        this.songRepository = songRepository;
    }

    public Long addSongToPlaylist(Long playlistId, Long songId) {
        boolean exists = songInPlaylistRepository.existsByPlaylistIdAndSongId(playlistId, songId);
        if (exists) {
            throw new BusinessException(ErrorCode.DUPLICATE_SONG_IN_PLAYLIST);
        }

        SongInPlaylist songInPlaylist = new SongInPlaylist();
        songInPlaylist.setPlaylistId(playlistId);
        songInPlaylist.setSongId(songId);

        SongInPlaylist savedSongInPlaylist = songInPlaylistRepository.save(songInPlaylist);
        return savedSongInPlaylist.getSongInPlaylistId();
    }

    public List<ResSongInPlaylistDto> getSongsInPlaylist(Long playlistId) {
        List<SongInPlaylist> songsInPlaylist = songInPlaylistRepository.findByPlaylistId(playlistId);
        List<ResSongInPlaylistDto> songsInPlaylistWithDetails = new ArrayList<>();

        for (SongInPlaylist songInPlaylist : songsInPlaylist) {
            Song song = songRepository.findById(songInPlaylist.getSongId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
            songsInPlaylistWithDetails.add(new ResSongInPlaylistDto(songInPlaylist.getSongInPlaylistId(), songInPlaylist.getPlaylistId(), song));
        }

//        for (SongInPlaylistWithDetailsDto songInPlaylistWithDetailsDto : songsInPlaylistWithDetails) {
//            System.out.println("SongInPlaylistWithDetailsDto: " + songInPlaylistWithDetailsDto.getPlaylistId());
//        }

        return songsInPlaylistWithDetails;
    }

    public void deleteSongFromPlaylist(Long songInPlaylistId) {
        SongInPlaylist songInPlaylist = songInPlaylistRepository.findById(songInPlaylistId).orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
        songInPlaylistRepository.delete(songInPlaylist);
    }
}
