package com.tlc.cansingtone.dto.like;

import com.tlc.cansingtone.domain.Song;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "좋아요 리스트에 포함된 곡 response")
public class ResSongInLikeListDto {


    @Schema(description = "좋아요 ID", example = "1")
    private Long likeId;

    @Schema(description = "회원 ID", example = "1")
    private String userId;

    @Schema(description = "곡 정보", example = "곡 정보")
    private Song songInfo;

    public ResSongInLikeListDto(Long likeId, String userId, Song song) {
        this.likeId = likeId;
        this.userId = userId;
        this.songInfo = song;
    }

}
