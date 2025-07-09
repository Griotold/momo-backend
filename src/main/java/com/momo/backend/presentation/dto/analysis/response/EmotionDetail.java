package com.momo.backend.presentation.dto.analysis.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "감정 분석 상세 정보")
public record EmotionDetail(
        @Schema(description = "전체적인 기분", example = "우울감")
        String overallMood,

        @Schema(description = "기분 점수 (1-10)", example = "3.2")
        double moodScore,

        @Schema(description = "주요 감정들", example = "[\"sadness\", \"fatigue\", \"apathy\"]")
        List<String> dominantEmotions,

        @Schema(description = "위험 수준", example = "medium", allowableValues = {"low", "medium", "high"})
        String riskLevel,

        @Schema(description = "분석 요약", example = "지난 주 동안 다소 우울한 기분이 지속되었습니다...")
        String summary
) {
}