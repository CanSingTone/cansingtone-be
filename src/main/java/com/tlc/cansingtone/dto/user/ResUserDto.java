package com.tlc.cansingtone.dto.user;

import com.tlc.cansingtone.domain.User;

import lombok.Getter;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(title = "회원 상세 조회 response")
public class ResUserDto {
    public ResUserDto(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.gender = user.getGender();
        this.ages = user.getAges();
        this.pref_genre1 = user.getPref_genre1();
        this.pref_genre2 = user.getPref_genre2();
        this.pref_genre3 = user.getPref_genre3();
        this.vocal_range_high = user.getVocalRangeHigh();
        this.vocal_range_low = user.getVocalRangeLow();
    }

//    public UserResponseDto(String userId, String nickname, int gender, int ages, int pref_genre1, int pref_genre2, int pref_genre3) {
//        this.userId = userId;
//        this.nickname = nickname;
//    }

    @Schema(description = "회원ID", example = "1")
    private String userId;

    @Schema(description = "회원 닉네임", example = "cansingtone")
    private String nickname;

    @Schema(description = "성별", example = "1")
    private int gender;

    @Schema(description = "나이대", example = "20")
    private int ages;

    @Schema(description = "선호장르1", example = "3")
    private int pref_genre1;

    @Schema(description = "선호장르2", example = "5")
    private int pref_genre2;

    @Schema(description = "선호장르3", example = "1")
    private int pref_genre3;

    @Schema(description = "회원 음역대 고음", example = "10")
    private int vocal_range_high;

    @Schema(description = "회원 음역대 저음", example = "1")
    private int vocal_range_low;


}
