package com.momo.backend.presentation.dto.analysis.response;

import com.momo.backend.common.dto.PaginationInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "분석 결과 목록 조회 응답")
public record AnalysisListResponse(
        @Schema(description = "분석 목록")
        List<AnalysisListItem> analyses,

        @Schema(description = "페이징 정보")
        PaginationInfo pagination
) {
}