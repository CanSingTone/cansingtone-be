package com.tlc.cansingtone.repository;

import com.tlc.cansingtone.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    Optional<User> findByUserId(String userId);

    Optional<Object> findByNickname(String nickname);
}
