package com.tlc.cansingtone.repository;

import com.tlc.cansingtone.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long>, JpaSpecificationExecutor<Song> {
    List<Song> findAll();

    Optional<Song> findBySongId(Long songId);

    List<Song> findByHighestNoteLessThanEqualAndLowestNoteGreaterThanEqual(int highestNote, int lowestNote);

}
