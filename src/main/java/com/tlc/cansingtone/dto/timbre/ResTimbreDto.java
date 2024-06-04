package com.tlc.cansingtone.dto.timbre;

import com.tlc.cansingtone.domain.Timbre;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResTimbreDto {
    private Long timbreId;
    private String timbreName;
    private String timbreUrl;
    private String userId;

    public ResTimbreDto(Timbre timbre) {
        this.timbreId = timbre.getTimbreId();
        this.timbreName = timbre.getTimbreName();
        this.timbreUrl = timbre.getTimbreUrl();
        this.userId = timbre.getUserId();
    }
}
