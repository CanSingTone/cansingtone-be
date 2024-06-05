package com.tlc.cansingtone.service;

import com.tlc.cansingtone.domain.User;
import com.tlc.cansingtone.dto.user.ResUserDto;
import com.tlc.cansingtone.repository.UserRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createNewUser(String userId, String nickname, int gender, int ages, int pref_genre1, int pref_genre2, int pref_genre3) {
        // 중복 아이디 체크
        if (userRepository.findByUserId(userId).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_ID);
        }

        // 중복 닉네임 체크
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_NICKNAME);
        }

        // 중복이 없으면 새로운 사용자 생성
        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setNickname(nickname);
        newUser.setGender(gender);
        newUser.setAges(ages);
        newUser.setPref_genre1(pref_genre1);
        newUser.setPref_genre2(pref_genre2);
        newUser.setPref_genre3(pref_genre3);

        User savedUser = userRepository.save(newUser);
        return savedUser.getUserId();
    }

    public boolean checkUserByUserId(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public ResUserDto getUserOne(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
        ResUserDto response = new ResUserDto(user);
        return response;
    }

    public List<User> findUsersWithSimilarVocalRange(String userId, int vocalRangeHigh, int vocalRangeLow) {
        // 사용자의 음역대 범위를 정의합니다.
        int vocalRangeThreshold = 2; // 음역대의 차이가 2 이내인 경우 유사한 음역대로 간주


        int currentUserVocalRangeHigh = vocalRangeHigh;
        int currentUserVocalRangeLow = vocalRangeLow;

        // 유사한 음역대를 가진 사용자를 검색합니다.
        List<User> similarUsers = userRepository.findUsersByVocalRange(
                currentUserVocalRangeHigh - vocalRangeThreshold,
                currentUserVocalRangeHigh + vocalRangeThreshold,
                currentUserVocalRangeLow - vocalRangeThreshold,
                currentUserVocalRangeLow + vocalRangeThreshold
        );

        // 자기 자신을 제외합니다.
        similarUsers.removeIf(user -> user.getUserId().equals(userId));

        return similarUsers;
    }


    @Transactional
    public User patchUser(String userId, String nickname, Integer ages, Integer pref_genre1, Integer pref_genre2, Integer pref_genre3) {
        // Find the user by userId
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));

        // Update user information if the corresponding parameters are not null
        if (nickname != null) {
            user.setNickname(nickname);
        }
        if (ages != null) {
            user.setAges(ages);
        }
        if (pref_genre1 != null) {
            user.setPref_genre1(pref_genre1);
        }
        if (pref_genre2 != null) {
            user.setPref_genre2(pref_genre2);
        }
        if (pref_genre3 != null) {
            user.setPref_genre3(pref_genre3);
        }

        // Save the updated user
        return userRepository.save(user);
    }


    public User patchVocalRange(String userId, int vocalRangeHigh, int vocalRangeLow) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));

        // Update the vocal range information
        user.setVocalRangeHigh(vocalRangeHigh);
        user.setVocalRangeLow(vocalRangeLow);

        // Save the updated user
        return userRepository.save(user);
    }

//    public User updateTimbre(String userId, String timbre) {
//        User user = userRepository.findByUserId(userId)
//                .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
//
//        // Update the timbre information
//        user.setTimbre(timbre);
//
//        // Save the updated user
//        return userRepository.save(user);
//    }
}
