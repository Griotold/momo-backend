package com.momo.backend.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "카카오 로그인 요청")
public record KakaoLoginRequest(
        @Schema(description = "카카오에서 받은 인가코드", example = "abc123def456")
        @NotBlank(message = "인가코드는 필수입니다.")
        String authorizationCode
) {
}
