package com.momo.backend.presentation.dto.analysis.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "분석 대상 기간")
public record AnalyzedPeriod(
        @Schema(description = "시작 날짜", example = "2025-01-01")
        String startDate,

        @Schema(description = "종료 날짜", example = "2025-01-07")
        String endDate
) {
}
