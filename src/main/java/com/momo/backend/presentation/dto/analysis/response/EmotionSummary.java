package com.momo.backend.presentation.dto.analysis.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "감정 분석 요약 정보 (목록용)")
public record EmotionSummary(
        @Schema(description = "전체적인 기분", example = "우울감")
        String overallMood,

        @Schema(description = "기분 점수 (1-10)", example = "3.2")
        double moodScore,

        @Schema(description = "위험 수준", example = "medium", allowableValues = {"low", "medium", "high"})
        String riskLevel
) {
}