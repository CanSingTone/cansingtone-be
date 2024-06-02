package com.tlc.cansingtone.dto.song;

import com.tlc.cansingtone.domain.Song;

import lombok.Getter;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(title = "노래 상세 조회 response")
public class ResSongDto {
    public ResSongDto(Song song) {
        this.songId = song.getSongId();
        this.songTitle = song.getSongTitle();
        this.albumImage = song.getAlbumImage();
        this.artist = song.getArtist();
        this.genre = song.getGenre();
        this.artistGender = song.getArtistGender();
        this.songVidUrl = song.getSongVidUrl();
        this.mrVidUrl = song.getMrVidUrl();
        this.highestNote = song.getHighestNote();
        this.lowestNote = song.getLowestNote();
    }

    @Schema(description = "노래ID", example = "1")
    private Long songId;

    @Schema(description = "노래명", example = "cansingtone")
    private String songTitle;

    @Schema(description = "앨범 이미지 URL", example = "https://www.cansingtone.com/album/1")
    private String albumImage;

    @Schema(description = "아티스트", example = "1")
    private String artist;

    @Schema(description = "장르", example = "20")
    private int genre;

    @Schema(description = "아티스트 성별", example = "3")
    private int artistGender;

    @Schema(description = "노래 영상 URL", example = "https://www.youtube.com/watch?v=1234")
    private String songVidUrl;

    @Schema(description = "MR 영상 URL", example = "https://www.youtube.com/watch?v=1234")
    private String mrVidUrl;

    @Schema(description = "노래 최고음", example = "10")
    private int highestNote;

    @Schema(description = "노래 최저음", example = "1")
    private int lowestNote;

    @Schema(description = "노래방 번호", example = "1234")
    private String karaoke_num;
}
