package com.tlc.cansingtone.repository;

import com.tlc.cansingtone.domain.SongInPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongInPlaylistRepository extends JpaRepository<SongInPlaylist, Long> {
    List<SongInPlaylist> findByPlaylistId(Long playlistId);

    boolean existsByPlaylistIdAndSongId(Long playlistId, Long songId);

    Optional<SongInPlaylist> findById(Long songInPlaylistId);
}
