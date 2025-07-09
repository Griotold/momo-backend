package com.momo.backend.presentation.dto.analysis.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "분석 목록 항목 (목록용)")
public record AnalysisListItem(
        @Schema(description = "분석 ID", example = "67890")
        Long analysisId,

        @Schema(description = "분석 타입", example = "weekly")
        String analysisType,

        @Schema(description = "분석 상태", example = "completed")
        String status,

        @Schema(description = "분석 대상 기간")
        AnalyzedPeriod analyzedPeriod,

        @Schema(description = "분석 대상 일기 수", example = "5")
        int diaryCount,

        @Schema(description = "감정 분석 요약 (완료된 경우만)")
        EmotionSummary emotionAnalysis,

        @Schema(description = "생성일시", example = "2025-01-07T15:30:00Z")
        String createdAt,

        @Schema(description = "완료일시 (완료된 경우만)", example = "2025-01-07T15:30:45Z")
        String completedAt
) {
}