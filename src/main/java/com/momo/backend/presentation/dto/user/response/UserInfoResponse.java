package com.momo.backend.presentation.dto.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "사용자 정보 응답")
public record UserInfoResponse(
        @Schema(description = "사용자 ID", example = "12345")
        Long id,

        @Schema(description = "소셜 ID", example = "987654321")
        String socialId,

        @Schema(description = "닉네임", example = "모모유저")
        String nickname,

        @Schema(description = "이메일 (동의 안하면 null)")
        String email,

        @Schema(description = "휴대폰 번호 (동의 안하면 null)")
        String phoneNumber,

        @Schema(description = "프로필 이미지 URL")
        String profileImage,

        @Schema(description = "계정 생성일", example = "2025-01-04T10:30:00Z")
        LocalDateTime createdAt,

        @Schema(description = "앱 잠금 설정 여부", example = "false")
        boolean appLockEnabled,

        @Schema(description = "동의한 스코프 목록")
        List<String> consentedScopes
) {
}
