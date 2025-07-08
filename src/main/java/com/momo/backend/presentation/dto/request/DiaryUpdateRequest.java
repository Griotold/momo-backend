package com.momo.backend.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "감정 일기 수정 요청")
public record DiaryUpdateRequest(
        @Schema(description = "감정 타입", example = "calm")
        @NotBlank(message = "감정 타입은 필수입니다.")
        String emotionType,

        @Schema(description = "일기 내용", example = "처음엔 기뻤는데 생각해보니 차분한 하루였다. 내음을 조금 수정한다.")
        @NotBlank(message = "일기 내용은 필수입니다.")
        String content
) {
}