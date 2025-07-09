package com.momo.backend.presentation.dto.analysis.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "분석 결과 피드백 제출 요청")
public record AnalysisFeedbackRequest(
        @Schema(description = "분석 결과 정확성 여부", example = "true")
        @NotNull(message = "정확성 여부는 필수입니다.")
        Boolean isAccurate,

        @Schema(description = "피드백 타입", example = "emotion_analysis",
                allowableValues = {"emotion_analysis", "recommendations", "overall"})
        @NotBlank(message = "피드백 타입은 필수입니다.")
        String feedbackType,

        @Schema(description = "피드백 코멘트", example = "분석이 정확해요. 실제로 우울한 기분이었거든요.")
        String comment
) {
}