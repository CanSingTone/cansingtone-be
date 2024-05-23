package com.tlc.cansingtone.repository;

import com.tlc.cansingtone.domain.Playlist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findAll();

    List<Playlist> findByUserId(String userId);

    Optional<Playlist> findByPlaylistId(Long playlistId);
}
