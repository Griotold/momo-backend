package com.momo.backend.presentation.dto.lock.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "앱 잠금 해제 요청")
public record LockUnlockRequest(
        @Schema(description = "6자리 잠금 비밀번호", example = "123456")
        @NotBlank(message = "잠금 비밀번호는 필수입니다.")
        @Pattern(regexp = "^[0-9]{6}$", message = "잠금 비밀번호는 6자리 숫자여야 합니다.")
        String password
) {
}
