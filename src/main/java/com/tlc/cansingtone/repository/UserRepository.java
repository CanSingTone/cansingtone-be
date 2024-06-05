package com.tlc.cansingtone.repository;

import com.tlc.cansingtone.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    Optional<User> findByUserId(String userId);

    Optional<Object> findByNickname(String nickname);

    @Query("SELECT u FROM User u WHERE u.vocalRangeHigh BETWEEN :lowHigh AND :highHigh AND u.vocalRangeLow BETWEEN :lowLow AND :highLow")
    List<User> findUsersByVocalRange(@Param("lowHigh") int lowHigh, @Param("highHigh") int highHigh,
                                     @Param("lowLow") int lowLow, @Param("highLow") int highLow);
}
