package com.momo.backend.presentation.dto.analysis.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "분석 상세 정보")
public record AnalysisDetail(
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

        @Schema(description = "감정 분석 상세 정보")
        EmotionDetail emotionAnalysis,

        @Schema(description = "AI 추천사항 목록")
        List<Recommendation> recommendations,

        @Schema(description = "생성일시", example = "2025-01-07T15:30:00Z")
        String createdAt,

        @Schema(description = "완료일시", example = "2025-01-07T15:30:45Z")
        String completedAt
) {
}