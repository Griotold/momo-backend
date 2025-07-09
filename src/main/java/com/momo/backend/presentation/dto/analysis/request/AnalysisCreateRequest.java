package com.momo.backend.presentation.dto.analysis.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "AI 감정 분석 생성 요청")
public record AnalysisCreateRequest(
        @Schema(description = "분석 타입", example = "weekly", allowableValues = {"daily", "weekly", "monthly"})
        @NotBlank(message = "분석 타입은 필수입니다.")
        String analysisType
) {
}