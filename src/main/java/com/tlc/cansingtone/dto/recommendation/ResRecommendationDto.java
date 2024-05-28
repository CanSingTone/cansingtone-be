package com.tlc.cansingtone.dto.recommendation;

import com.tlc.cansingtone.domain.Recommendation;
import com.tlc.cansingtone.domain.Song;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResRecommendationDto {
    @Schema(description = "추천 ID", example = "1")
    private Long recommendationId;

    @Schema(description = "사용자 ID", example = "1")
    private String userId;

    @Schema(description = "추천 방법", example = "1")
    private String recommendationMethod;

    @Schema(description = "추천 날짜", example = "1")
    private String recommendationDate;

    @Schema(description = "곡 정보", example = "곡 정보")
    private Song songInfo;


    public ResRecommendationDto(Recommendation recommendation, Song song) {
        this.recommendationId = recommendation.getRecommendationId();
        this.userId = recommendation.getUserId();
        this.recommendationMethod = recommendation.getRecommendationMethod();
        this.recommendationDate = recommendation.getRecommendationDate();
        this.songInfo = song;
    }

}
