package com.momo.backend.presentation.dto.diary.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "일기 정보")
public record DiaryInfo(
        @Schema(description = "일기 ID", example = "12345")
        Long id,

        @Schema(description = "감정 타입", example = "happy")
        String emotionType,

        @Schema(description = "일기 내용", example = "오늘은 정말 좋은 하루였다. 친구들과 맛있는 음식도 먹고 영화도 봤다.")
        String content,

        @Schema(description = "일기 날짜", example = "2025-01-04")
        String diaryDate,

        @Schema(description = "생성일시", example = "2025-01-04T22:30:00Z")
        String createdAt,

        @Schema(description = "수정일시", example = "2025-01-04T22:30:00Z")
        String updatedAt
) {
}