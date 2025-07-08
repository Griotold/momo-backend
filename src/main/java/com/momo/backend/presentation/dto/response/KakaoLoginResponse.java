package com.momo.backend.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "카카오 로그인 응답")
public record KakaoLoginResponse(
        @Schema(description = "액세스 토큰")
        String accessToken,

        @Schema(description = "리프레시 토큰")
        String refreshToken,

        @Schema(description = "사용자 정보")
        UserInfo user
) {
    @Schema(description = "사용자 상세 정보")
    public record UserInfo(
            @Schema(description = "사용자 ID", example = "12345")
            Long id,

            @Schema(description = "소셜 ID", example = "987654321")
            String socialId,

            @Schema(description = "닉네임", example = "모모유저 (동의 안하면 null)")
            String nickname,

            @Schema(description = "이메일", example = "test@email.com (동의 안하면 null)")
            String email,

            @Schema(description = "휴대폰 번호", example = "010-1234-5678 (동의 안하면 null)")
            String phoneNumber,

            @Schema(description = "신규 유저 여부", example = "true")
            boolean isNewUser,

            @Schema(description = "동의한 스코프 목록")
            List<String> consentedScopes
    ) {}
}
