package com.momo.backend.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "페이징 정보")
public record PaginationInfo(
        @Schema(description = "현재 페이지", example = "1")
        int currentPage,

        @Schema(description = "전체 페이지 수", example = "5")
        int totalPages,

        @Schema(description = "전체 항목 수", example = "98")
        long totalCount,

        @Schema(description = "다음 페이지 존재 여부", example = "true")
        boolean hasNext,

        @Schema(description = "이전 페이지 존재 여부", example = "false")
        boolean hasPrevious
) {
}