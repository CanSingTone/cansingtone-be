package com.tlc.cansingtone.dto.playlist;

import com.tlc.cansingtone.domain.Playlist;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "플레이리스트 상세 조회 response")
public class ResPlaylistDto {
    public ResPlaylistDto(Playlist playlist) {
        this.playlistId = playlist.getPlaylistId();
        this.userId = playlist.getUserId();
        this.playlistName = playlist.getPlaylistName();
        this.isPublic = playlist.getIsPublic();
    }

    @Schema(description = "플레이리스트 ID", example = "1")
    private Long playlistId;

    @Schema(description = "회원 ID", example = "1")
    private String userId;

    @Schema(description = "플레이리스트 이름", example = "playlist1")
    private String playlistName;

    @Schema(description = "공개 여부", example = "1")
    private int isPublic;
}
