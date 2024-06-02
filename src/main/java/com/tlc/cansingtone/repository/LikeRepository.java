package com.tlc.cansingtone.repository;

import com.tlc.cansingtone.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUserId(String userId);

    Optional<Like> findByUserIdAndSongId(String userId, Long songId);

}
