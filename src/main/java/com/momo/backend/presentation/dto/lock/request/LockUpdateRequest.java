package com.momo.backend.presentation.dto.lock.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "앱 잠금 비밀번호 변경 요청")
public record LockUpdateRequest(
        @Schema(description = "기존 6자리 잠금 비밀번호", example = "123456")
        @NotBlank(message = "기존 비밀번호는 필수입니다.")
        @Pattern(regexp = "^[0-9]{6}$", message = "기존 비밀번호는 6자리 숫자여야 합니다.")
        String oldPassword,

        @Schema(description = "새 6자리 잠금 비밀번호", example = "654321")
        @NotBlank(message = "새 비밀번호는 필수입니다.")
        @Pattern(regexp = "^[0-9]{6}$", message = "새 비밀번호는 6자리 숫자여야 합니다.")
        String newPassword,

        @Schema(description = "새 비밀번호 확인", example = "654321")
        @NotBlank(message = "비밀번호 확인은 필수입니다.")
        String confirmPassword
) {
}
