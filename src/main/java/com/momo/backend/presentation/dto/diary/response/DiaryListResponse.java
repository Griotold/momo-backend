package com.momo.backend.presentation.dto.diary.response;

import com.momo.backend.common.dto.PaginationInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "감정 일기 목록 조회 응답")
public record DiaryListResponse(
        @Schema(description = "일기 목록")
        List<DiaryInfo> diaries,

        @Schema(description = "페이징 정보")
        PaginationInfo pagination
) {

}
