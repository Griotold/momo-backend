package com.momo.backend.presentation.dto.diary.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "감정 일기 작성 요청")
public record DiaryCreateRequest(
        @Schema(description = "감정 타입", example = "happy")
        @NotBlank(message = "감정 타입은 필수입니다.")
        String emotionType,

        @Schema(description = "일기 내용", example = "오늘은 정말 좋은 하루였다. 친구들과 맛있는 음식도 먹고 영화도 봤다.")
        @NotBlank(message = "일기 내용은 필수입니다.")
        String content
) {
}