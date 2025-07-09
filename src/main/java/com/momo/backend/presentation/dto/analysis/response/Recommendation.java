package com.momo.backend.presentation.dto.analysis.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "AI 추천사항")
public record Recommendation(
        @Schema(description = "추천 타입", example = "walking", allowableValues = {"walking", "meditation", "shower", "nap"})
        String type,

        @Schema(description = "추천 제목", example = "가벼운 산책")
        String title,

        @Schema(description = "추천 설명", example = "10-15분 정도 집 근처를 천천히 걸어보세요.")
        String description,

        @Schema(description = "예상 소요 시간 (분)", example = "15")
        int duration,

        @Schema(description = "우선순위", example = "high", allowableValues = {"low", "medium", "high"})
        String priority
) {
}