package com.tlc.cansingtone.repository;

import com.tlc.cansingtone.domain.Timbre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TimbreRepository extends JpaRepository<Timbre, Long> {
    List<Timbre> findByUserId(String userId);

    Optional<Timbre> findByTimbreId(Long timbreId);

    @Query("SELECT COUNT(t) FROM Timbre t WHERE t.userId = :userId")
    int getTimbreCountForUser(@Param("userId") String userId);
}
