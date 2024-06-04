package com.tlc.cansingtone.dto.recommendation;

import com.tlc.cansingtone.domain.recommendation.TimbreBasedRecommendation;
import com.tlc.cansingtone.domain.Song;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResTimbreBasedRecommendationDto {
    @Schema(description = "추천 ID", example = "1")
    private Long recommendationId;

    @Schema(description = "사용자 ID", example = "1")
    private String userId;

    @Schema(description = "추천 날짜", example = "1")
    private String recommendationDate;

    @Schema(description = "곡 정보", example = "곡 정보")
    private Song songInfo;

    @Schema(description = "음색 ID", example = "1")
    private Long timbreId;


    public ResTimbreBasedRecommendationDto(TimbreBasedRecommendation timbreBasedRecommendation, Song song) {
        this.recommendationId = timbreBasedRecommendation.getRecommendationId();
        this.userId = timbreBasedRecommendation.getUserId();
        this.recommendationDate = timbreBasedRecommendation.getRecommendationDate();
        this.songInfo = song;
        this.timbreId = timbreBasedRecommendation.getTimbreId();
    }
}
