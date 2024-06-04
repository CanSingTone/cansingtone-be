package com.tlc.cansingtone.repository;

import com.tlc.cansingtone.domain.Timbre;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TimbreRepository extends JpaRepository<Timbre, Long> {
    List<Timbre> findByUserId(String userId);

    Optional<Timbre> findByTimbreId(Long timbreId);
}
