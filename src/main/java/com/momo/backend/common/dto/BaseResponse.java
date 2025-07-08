package com.momo.backend.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "공통 응답 DTO")
public record BaseResponse<T>(
        @Schema(description = "성공 여부", example = "true")
        boolean success,
        @Schema(description = "데이터")
        T data
) {

}
