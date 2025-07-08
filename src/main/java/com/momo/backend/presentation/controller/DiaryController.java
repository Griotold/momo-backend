package com.momo.backend.presentation.controller;

import com.momo.backend.common.dto.BaseResponse;
import com.momo.backend.common.dto.PaginationInfo;
import com.momo.backend.presentation.dto.request.DiaryCreateRequest;
import com.momo.backend.presentation.dto.request.DiaryUpdateRequest;
import com.momo.backend.presentation.dto.response.DiaryCalendarResponse;
import com.momo.backend.presentation.dto.response.DiaryCreateResponse;
import com.momo.backend.presentation.dto.response.DiaryInfo;
import com.momo.backend.presentation.dto.response.DiaryListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Tag(name = "감정 일기 컨트롤러", description = "감정 일기 관리 API - 인증 필요")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/diaries")
@RestController
public class DiaryController {

    // 감정 일기 작성
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "감정 일기 작성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DiaryCreateResponse.class),
                            examples = @ExampleObject(
                                    name = "감정 일기 작성 성공",
                                    summary = "감정 일기 작성 성공 응답",
                                    value = """
                                    {
                                      "success": true,
                                      "data": {
                                        "id": 12345,
                                        "emotionType": "happy",
                                        "content": "오늘은 정말 좋은 하루였다. 친구들과 맛있는 음식도 먹고 영화도 봤다.",
                                        "diaryDate": "2025-01-04",
                                        "createdAt": "2025-01-04T22:30:00Z",
                                        "updatedAt": "2025-01-04T22:30:00Z"
                                      }
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (필수값 누락, 유효성 검증 실패)",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Operation(
            summary = "감정 일기 작성",
            description = "인증된 사용자의 감정 일기를 작성합니다. emotionType과 content가 필요합니다."
    )
    public ResponseEntity<BaseResponse<DiaryCreateResponse>> createDiary(
            @RequestBody @Valid DiaryCreateRequest request
    ) {
        log.info("감정 일기 작성 요청 - 감정: {}, 내용 길이: {}", request.emotionType(), request.content().length());

        // Mock 데이터 생성
        LocalDateTime now = LocalDateTime.now();
        DiaryCreateResponse mockResponse = new DiaryCreateResponse(
                12345L,
                request.emotionType(),
                request.content(),
                LocalDate.now(),  // 오늘 날짜
                now,              // 생성일시
                now               // 수정일시 (처음 생성시에는 동일)
        );

        BaseResponse<DiaryCreateResponse> response = new BaseResponse<>(true, mockResponse);

        log.info("감정 일기 작성 응답: ID {}, 감정 {}", mockResponse.id(), mockResponse.emotionType());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 감정 일기 목록 조회(페이징)
     * */
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "감정 일기 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DiaryListResponse.class),
                            examples = @ExampleObject(
                                    name = "감정 일기 목록 조회 성공",
                                    summary = "감정 일기 목록 조회 성공 응답",
                                    value = """
                                    {
                                      "success": true,
                                      "data": {
                                        "diaries": [
                                          {
                                            "id": 12345,
                                            "emotionType": "happy",
                                            "content": "오늘은 정말 좋은 하루였다. 친구들과 맛있는 음식도 먹고 영화도 봤다.",
                                            "diaryDate": "2025-01-04",
                                            "createdAt": "2025-01-04T22:30:00Z",
                                            "updatedAt": "2025-01-04T22:30:00Z"
                                          },
                                          {
                                            "id": 12344,
                                            "emotionType": "calm",
                                            "content": "조용한 카페에서 책을 읽으며 평온한 시간을 보냈다.",
                                            "diaryDate": "2025-01-03",
                                            "createdAt": "2025-01-03T21:15:00Z",
                                            "updatedAt": "2025-01-03T21:15:00Z"
                                          }
                                        ],
                                        "pagination": {
                                          "currentPage": 1,
                                          "totalPages": 5,
                                          "totalCount": 98,
                                          "hasNext": true,
                                          "hasPrevious": false
                                        }
                                      }
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호 (1부터 시작)", example = "1"),
            @Parameter(name = "size", description = "페이지 크기", example = "20"),
            @Parameter(name = "emotionType", description = "감정 타입 필터", example = "happy"),
            @Parameter(name = "startDate", description = "조회 시작 날짜", example = "2025-01-01")
    })
    @Operation(
            summary = "감정 일기 목록 조회",
            description = "인증된 사용자의 감정 일기 목록을 페이징으로 조회합니다. 감정 타입과 날짜로 필터링 가능합니다."
    )
    public ResponseEntity<BaseResponse<DiaryListResponse>> getDiaries(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String emotionType,
            @RequestParam(required = false) String startDate
    ) {
        log.info("감정 일기 목록 조회 요청 - 페이지: {}, 크기: {}, 감정: {}, 시작날짜: {}",
                page, size, emotionType, startDate);

        // Mock 데이터 생성
        List<DiaryInfo> mockDiaries = List.of(
                new DiaryInfo(
                        12345L,
                        "happy",
                        "오늘은 정말 좋은 하루였다. 친구들과 맛있는 음식도 먹고 영화도 봤다.",
                        "2025-01-04",
                        "2025-01-04T22:30:00Z",
                        "2025-01-04T22:30:00Z"
                ),
                new DiaryInfo(
                        12344L,
                        "calm",
                        "조용한 카페에서 책을 읽으며 평온한 시간을 보냈다.",
                        "2025-01-03",
                        "2025-01-03T21:15:00Z",
                        "2025-01-03T21:15:00Z"
                )
        );

        PaginationInfo mockPagination = new PaginationInfo(
                page,           // currentPage
                5,              // totalPages
                98L,            // totalCount
                page < 5,       // hasNext
                page > 1        // hasPrevious
        );

        DiaryListResponse mockResponse = new DiaryListResponse(mockDiaries, mockPagination);
        BaseResponse<DiaryListResponse> response = new BaseResponse<>(true, mockResponse);

        log.info("감정 일기 목록 조회 응답: 일기 수 {}, 페이지 {}/{}",
                mockDiaries.size(), page, mockPagination.totalPages());
        return ResponseEntity.ok(response);
    }

    // 특정 감정 일기 조회
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "특정 감정 일기 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DiaryInfo.class),
                            examples = @ExampleObject(
                                    name = "특정 감정 일기 조회 성공",
                                    summary = "특정 감정 일기 조회 성공 응답",
                                    value = """
                                {
                                  "success": true,
                                  "data": {
                                    "id": 12345,
                                    "emotionType": "happy",
                                    "content": "오늘은 정말 좋은 하루였다. 친구들과 맛있는 음식도 먹고 영화도 봤다.",
                                    "diaryDate": "2025-01-04",
                                    "createdAt": "2025-01-04T22:30:00Z",
                                    "updatedAt": "2025-01-04T22:30:00Z"
                                  }
                                }
                                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "일기를 찾을 수 없음",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Parameter(name = "id", description = "일기 ID", example = "12345")
    @Operation(
            summary = "특정 감정 일기 조회",
            description = "인증된 사용자의 특정 감정 일기를 조회합니다."
    )
    public ResponseEntity<BaseResponse<DiaryInfo>> getDiary(@PathVariable("id") Long id) {
        log.info("특정 감정 일기 조회 요청 - ID: {}", id);

        // Mock 데이터 생성
        DiaryInfo mockResponse = new DiaryInfo(
                id,  // 요청받은 ID 그대로 사용
                "happy",
                "오늘은 정말 좋은 하루였다. 친구들과 맛있는 음식도 먹고 영화도 봤다.",
                "2025-01-04",
                "2025-01-04T22:30:00Z",
                "2025-01-04T22:30:00Z"
        );

        BaseResponse<DiaryInfo> response = new BaseResponse<>(true, mockResponse);

        log.info("특정 감정 일기 조회 응답 - ID: {}, 감정: {}", id, mockResponse.emotionType());
        return ResponseEntity.ok(response);
    }

    /**
     * 감정 일기 수정
     * */
    // 감정 일기 수정
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "감정 일기 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DiaryInfo.class),
                            examples = @ExampleObject(
                                    name = "감정 일기 수정 성공",
                                    summary = "감정 일기 수정 성공 응답",
                                    value = """
                                {
                                  "success": true,
                                  "data": {
                                    "id": 12345,
                                    "emotionType": "calm",
                                    "content": "처음엔 기뻤는데 생각해보니 차분한 하루였다. 내음을 조금 수정한다.",
                                    "diaryDate": "2025-01-04",
                                    "createdAt": "2025-01-04T22:30:00Z",
                                    "updatedAt": "2025-01-04T23:15:00Z"
                                  }
                                }
                                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "일기를 찾을 수 없음",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (필수값 누락, 유효성 검증 실패)",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Parameter(name = "id", description = "수정할 일기 ID", example = "12345")
    @Operation(
            summary = "감정 일기 수정",
            description = "인증된 사용자의 특정 감정 일기를 수정합니다. 감정 타입과 일기 내용이 필요합니다."
    )
    public ResponseEntity<BaseResponse<DiaryInfo>> updateDiary(
            @PathVariable("id") Long id,
            @RequestBody @Valid DiaryUpdateRequest request
    ) {
        log.info("감정 일기 수정 요청 - ID: {}, 감정: {}, 내용 길이: {}",
                id, request.emotionType(), request.content().length());

        // Mock 데이터 생성 (수정된 내용 반영)
        DiaryInfo mockResponse = new DiaryInfo(
                id,  // 요청받은 ID 그대로 사용
                request.emotionType(),  // 수정된 감정 타입
                request.content(),      // 수정된 내용
                "2025-01-04",          // 일기 날짜는 변경되지 않음
                "2025-01-04T22:30:00Z", // 생성일시는 변경되지 않음
                "2025-01-04T23:15:00Z"  // 수정일시는 현재 시간으로 업데이트
        );

        BaseResponse<DiaryInfo> response = new BaseResponse<>(true, mockResponse);

        log.info("감정 일기 수정 응답 - ID: {}, 새로운 감정: {}", id, mockResponse.emotionType());
        return ResponseEntity.ok(response);
    }

    // 감정 일기 삭제
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "감정 일기 삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(
                                    name = "감정 일기 삭제 성공",
                                    summary = "감정 일기 삭제 성공 응답",
                                    value = """
                                {
                                  "success": true,
                                  "message": "일기가 삭제되었습니다"
                                }
                                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "일기를 찾을 수 없음",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Parameter(name = "id", description = "삭제할 일기 ID", example = "12345")
    @Operation(
            summary = "감정 일기 삭제",
            description = "인증된 사용자의 특정 감정 일기를 삭제합니다."
    )
    public ResponseEntity<BaseResponse<String>> deleteDiary(
            @PathVariable("id") Long id
    ) {
        log.info("감정 일기 삭제 요청 - ID: {}", id);

        // Mock 응답 - 삭제 성공 메시지 반환
        BaseResponse<String> response = new BaseResponse<>(true, "일기가 삭제되었습니다");

        log.info("감정 일기 삭제 응답 - ID: {} 삭제 완료", id);
        return ResponseEntity.ok(response);
    }

    /**
     * 감정 일기 캘린더 조회
     * */
    @GetMapping("/calendar")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "캘린더 형태 일기 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DiaryCalendarResponse.class),
                            examples = @ExampleObject(
                                    name = "캘린더 형태 일기 조회 성공",
                                    summary = "캘린더 형태 일기 조회 성공 응답",
                                    value = """
                                {
                                  "success": true,
                                  "data": {
                                    "year": 2025,
                                    "month": 1,
                                    "calendar": {
                                      "2025-01-01": {
                                        "id": 12345,
                                        "emotionType": "happy"
                                      },
                                      "2025-01-03": {
                                        "id": 12346,
                                        "emotionType": "sad"
                                      },
                                      "2025-01-04": {
                                        "id": 12347,
                                        "emotionType": "calm"
                                      }
                                    }
                                  }
                                }
                                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (년도/월 파라미터 오류)",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Parameters({
            @Parameter(name = "year", description = "조회할 년도", example = "2025"),
            @Parameter(name = "month", description = "조회할 월 (1-12)", example = "1")
    })
    @Operation(
            summary = "캘린더 형태로 일기 조회",
            description = "인증된 사용자의 특정 년월에 작성된 일기를 캘린더 형태로 조회합니다."
    )
    public ResponseEntity<BaseResponse<DiaryCalendarResponse>> getDiaryCalendar(
            @RequestParam int year,
            @RequestParam int month
    ) {
        log.info("캘린더 형태 일기 조회 요청 - 년도: {}, 월: {}", year, month);

        // Mock 데이터 생성 - 해당 월의 일기 데이터
        Map<String, DiaryCalendarResponse.CalendarDayInfo> mockCalendar = Map.of(
                "2025-01-01", new DiaryCalendarResponse.CalendarDayInfo(12345L, "happy"),
                "2025-01-03", new DiaryCalendarResponse.CalendarDayInfo(12346L, "sad"),
                "2025-01-04", new DiaryCalendarResponse.CalendarDayInfo(12347L, "calm")
        );

        DiaryCalendarResponse mockResponse = new DiaryCalendarResponse(
                year,
                month,
                mockCalendar
        );

        BaseResponse<DiaryCalendarResponse> response = new BaseResponse<>(true, mockResponse);

        log.info("캘린더 형태 일기 조회 응답 - 년도: {}, 월: {}, 일기 수: {}",
                year, month, mockCalendar.size());
        return ResponseEntity.ok(response);
    }

    // 오늘 일기 조회
    @GetMapping("/today")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "오늘 일기 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DiaryInfo.class),
                            examples = @ExampleObject(
                                    name = "오늘 일기 조회 성공",
                                    summary = "오늘 일기 조회 성공 응답",
                                    value = """
                                {
                                  "success": true,
                                  "data": {
                                    "id": 12345,
                                    "emotionType": "happy",
                                    "content": "오늘은 정말 좋은 하루였다.",
                                    "diaryDate": "2025-01-04",
                                    "createdAt": "2025-01-04T22:30:00Z",
                                    "updatedAt": "2025-01-04T22:30:00Z"
                                  }
                                }
                                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "오늘 작성된 일기를 찾을 수 없음",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Operation(
            summary = "오늘 일기 조회",
            description = "인증된 사용자의 오늘 작성된 일기를 조회합니다."
    )
    public ResponseEntity<BaseResponse<DiaryInfo>> getTodayDiary() {
        log.info("오늘 일기 조회 요청");

        // Mock 데이터 생성 - 오늘 일기
        DiaryInfo mockResponse = new DiaryInfo(
                12345L,
                "happy",
                "오늘은 정말 좋은 하루였다.",
                "2025-01-04",  // 오늘 날짜
                "2025-01-04T22:30:00Z",
                "2025-01-04T22:30:00Z"
        );

        BaseResponse<DiaryInfo> response = new BaseResponse<>(true, mockResponse);

        log.info("오늘 일기 조회 응답 - ID: {}, 감정: {}", mockResponse.id(), mockResponse.emotionType());
        return ResponseEntity.ok(response);
    }
}
