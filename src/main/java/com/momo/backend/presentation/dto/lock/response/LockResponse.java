package com.momo.backend.presentation.dto.lock.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "앱 잠금 생성 응답")
public record LockResponse(
        // todo user 테이블로 반정규화
        @Schema(description = "잠금 설정 ID", example = "12345")
        Long lockId,

        // todo isLocked 로 수정
        @Schema(description = "잠금 활성화 여부", example = "true")
        boolean isEnabled,

        @Schema(description = "생체 인증 사용 여부 (MVP에서는 항상 false)", example = "false")
        boolean useBiometric,

        @Schema(description = "설정 생성 시간", example = "2025-01-07T15:30:00Z")
        String createdAt,

        @Schema(description = "설정 변경 시간", example = "2025-01-08T16:48:00Z")
        String updatedAt
) {
}