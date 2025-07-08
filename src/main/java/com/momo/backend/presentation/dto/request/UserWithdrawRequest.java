package com.momo.backend.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "회원탈퇴 요청")
public record UserWithdrawRequest(
        @Schema(description = "탈퇴 이유", example = "앱을 더 이상 사용하지 않음")
        @NotBlank(message = "탈퇴 이유는 필수입니다.")
        String reason
) {
}
