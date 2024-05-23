package com.tlc.cansingtone.dto.like;

import com.tlc.cansingtone.domain.Like;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "좋아요 response")
public class ResLikeDto {

    public ResLikeDto(Like like) {
        this.likeId = like.getLikeId();
        this.userId = like.getUserId();
        this.songId = like.getSongId();
    }

    @Schema(description = "좋아요 ID", example = "1")
    private Long likeId;

    @Schema(description = "회원 ID", example = "1")
    private String userId;

    @Schema(description = "노래 ID", example = "1")
    private Long songId;
}
