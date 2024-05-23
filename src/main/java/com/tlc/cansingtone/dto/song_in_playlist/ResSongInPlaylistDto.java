package com.tlc.cansingtone.dto.song_in_playlist;

import com.tlc.cansingtone.domain.Song;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResSongInPlaylistDto {
    @Schema(description = "플레이리스트에 포함된 곡 ID", example = "1")
    private Long songInPlaylistId;

    @Schema(description = "플레이리스트 ID", example = "1")
    private Long playlistId;

//    @Schema(description = "곡 ID", example = "1")
//    private Long songId;
//
//    @Schema(description = "곡명", example = "밤양갱")
//    private String songTitle;
//
//    @Schema(description = "아티스트", example = "비비")
//    private String artist;
//
//    @Schema(description = "장르", example = "발라드")
//    private int genre;

    @Schema(description = "곡 정보", example = "곡 정보")
    private Song songInfo;


    public ResSongInPlaylistDto(Long songInPlaylistId, Long playlistId, Song song) {
        this.songInPlaylistId = songInPlaylistId;
        this.playlistId = playlistId;
        this.songInfo = song;
    }
}
