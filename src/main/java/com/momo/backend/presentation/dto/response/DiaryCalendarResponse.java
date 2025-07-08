package com.momo.backend.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

@Schema(description = "캘린더 형태 일기 조회 응답")
public record DiaryCalendarResponse(
        @Schema(description = "조회 년도", example = "2025")
        int year,

        @Schema(description = "조회 월", example = "1")
        int month,

        @Schema(description = "캘린더 데이터 (날짜별 일기 정보)")
        Map<String, CalendarDayInfo> calendar
) {
    @Schema(description = "캘린더 날짜별 일기 정보")
    public record CalendarDayInfo(
            @Schema(description = "일기 ID", example = "12345")
            Long id,

            @Schema(description = "감정 타입", example = "happy")
            String emotionType
    ) {}
}