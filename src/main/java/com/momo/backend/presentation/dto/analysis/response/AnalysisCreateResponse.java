package com.momo.backend.presentation.dto.analysis.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "AI 감정 분석 생성 응답")
public record AnalysisCreateResponse(
        @Schema(description = "분석 ID", example = "67890")
        Long analysisId,

        @Schema(description = "분석 타입", example = "weekly")
        String analysisType,

        @Schema(description = "분석 상태", example = "processing")
        String status,

        @Schema(description = "분석 대상 기간")
        AnalyzedPeriod analyzedPeriod,

        @Schema(description = "분석 대상 일기 수", example = "5")
        int diaryCount,

        @Schema(description = "예상 소요 시간", example = "30초")
        String estimatedTime,

        @Schema(description = "안내 메시지", example = "AI가 감정을 분석 중입니다...")
        String message,

        @Schema(description = "생성일시", example = "2025-01-07T15:30:00Z")
        String createdAt
) {

}