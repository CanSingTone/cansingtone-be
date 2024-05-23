package com.tlc.cansingtone.service;

import com.tlc.cansingtone.domain.Song;
import com.tlc.cansingtone.dto.song.ResSongDto;
import com.tlc.cansingtone.repository.SongRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.stereotype.Service;

import java.util.Collections;
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

    public List<Song> getSongsbyTitleOrArtist(String keyword) {
        List<Song> songs = songRepository.findByArtistContainingIgnoreCaseOrSongTitleContainingIgnoreCase(keyword, keyword);
        return songs;
    }

    public List<Song> getSongsbyGenreAndVocalRange(int highestNote, int lowestNote) {
        // highestNote가 null인 경우 가장 높은 음으로 설정
        if (highestNote == -1) {
            highestNote = Integer.MAX_VALUE;
        }
        // lowestNote가 null인 경우 가장 낮은 음으로 설정
        if (lowestNote == -1) {
            lowestNote = Integer.MIN_VALUE;
        }

        // highestNote가 입력받은 highestNote보다 낮고 lowestNote가 입력받은 lowestNote보다 높은 곡을 검색합니다.
        List<Song> songs = songRepository.findByHighestNoteLessThanAndLowestNoteGreaterThan(highestNote, lowestNote);
        return songs;
    }
}
