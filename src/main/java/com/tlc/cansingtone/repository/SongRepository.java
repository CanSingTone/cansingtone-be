package com.tlc.cansingtone.repository;

import com.tlc.cansingtone.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long>, JpaSpecificationExecutor<Song> {
    List<Song> findAll();

    Optional<Song> findBySongId(Long songId);

    // 아티스트 이름이나 곡 제목에 키워드를 포함하는 곡을 검색합니다.
    List<Song> findByArtistContainingIgnoreCaseOrSongTitleContainingIgnoreCase(String artist, String songTitle);

}
