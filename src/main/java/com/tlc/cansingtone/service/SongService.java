package com.tlc.cansingtone.service;

import com.tlc.cansingtone.domain.Song;
import com.tlc.cansingtone.dto.song.ResSongDto;
import com.tlc.cansingtone.repository.SongRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song> getSongs() {
        List<Song> songs = songRepository.findAll();
        return songs;
    }

    public ResSongDto getSongbySongId(Long songId) {
        Song song = songRepository.findBySongId(songId).orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
        ResSongDto response = new ResSongDto(song);
        return response;
    }

}
