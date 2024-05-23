package com.tlc.cansingtone.service;

import com.tlc.cansingtone.domain.User;
import com.tlc.cansingtone.dto.user.ResUserDto;
import com.tlc.cansingtone.repository.UserRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.stereotype.Service;

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

    public User patchVocalRange(String userId, int vocalRangeHigh, int vocalRangeLow) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));

        // Update the vocal range information
        user.setVocalRangeHigh(vocalRangeHigh);
        user.setVocalRangeLow(vocalRangeLow);

        // Save the updated user
        return userRepository.save(user);
    }

    public User updateTimbre(String userId, String timbre) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));

        // Update the timbre information
        user.setTimbre(timbre);

        // Save the updated user
        return userRepository.save(user);
    }
}
