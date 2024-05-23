package com.tlc.cansingtone.dto.like;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "좋아요 request")
public class ReqLikeDto {
    private String userId;
    private Long songId;

    public ReqLikeDto(String userId, Long songId) {
        this.userId = userId;
        this.songId = songId;
    }

    public String getUserId() {
        return userId;
    }

    public Long getSongId() {
        return songId;
    }
}
