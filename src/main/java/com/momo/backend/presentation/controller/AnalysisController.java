package com.momo.backend.presentation.controller;

import com.momo.backend.common.dto.BaseResponse;
import com.momo.backend.common.dto.PaginationInfo;
import com.momo.backend.presentation.dto.analysis.request.AnalysisCreateRequest;
import com.momo.backend.presentation.dto.analysis.request.AnalysisFeedbackRequest;
import com.momo.backend.presentation.dto.analysis.response.*;
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

import java.util.List;

@Tag(name = "감정 분석 컨트롤러", description = "AI 감정 분석 관련 API - 인증 필요")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/analysis")
@RestController
public class AnalysisController {

    /**
     * AI 감정 분석 생성
     * */
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "AI 감정 분석 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AnalysisCreateResponse.class),
                            examples = @ExampleObject(
                                    name = "AI 감정 분석 생성 성공",
                                    summary = "AI 감정 분석 생성 성공 응답",
                                    value = """
                                    {
                                      "success": true,
                                      "data": {
                                        "analysisId": 67890,
                                        "analysisType": "weekly",
                                        "status": "processing",
                                        "analyzedPeriod": {
                                          "startDate": "2025-01-01",
                                          "endDate": "2025-01-07"
                                        },
                                        "diaryCount": 5,
                                        "estimatedTime": "30초",
                                        "message": "AI가 감정을 분석 중입니다...",
                                        "createdAt": "2025-01-07T15:30:00Z"
                                      }
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (잘못된 분석 타입, 분석할 일기 없음)",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Operation(
            summary = "AI 감정 분석 생성",
            description = "인증된 사용자의 일기를 대상으로 AI 감정 분석을 요청합니다. 분석 타입에 따라 대상 기간이 결정됩니다."
    )
    public ResponseEntity<BaseResponse<AnalysisCreateResponse>> createAnalysis(
            @RequestBody @Valid AnalysisCreateRequest request
    ) {
        log.info("AI 감정 분석 생성 요청 - 분석 타입: {}", request.analysisType());

        // Mock 데이터 생성
        AnalyzedPeriod mockPeriod =
                new AnalyzedPeriod("2025-01-01", "2025-01-07");

        AnalysisCreateResponse mockResponse = new AnalysisCreateResponse(
                67890L,
                request.analysisType(),
                "processing",
                mockPeriod,
                5,  // 분석 대상 일기 수
                "30초",
                "AI가 감정을 분석 중입니다...",
                "2025-01-07T15:30:00Z"
        );

        BaseResponse<AnalysisCreateResponse> response = new BaseResponse<>(true, mockResponse);

        log.info("AI 감정 분석 생성 응답 - ID: {}, 타입: {}, 상태: {}",
                mockResponse.analysisId(), mockResponse.analysisType(), mockResponse.status());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 특정 분석 결과 조회
     * */
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "특정 분석 결과 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AnalysisDetail.class),
                            examples = @ExampleObject(
                                    name = "특정 분석 결과 조회 성공",
                                    summary = "완료된 분석 결과 응답",
                                    value = """
                                {
                                  "success": true,
                                  "data": {
                                    "analysisId": 67890,
                                    "analysisType": "weekly",
                                    "status": "completed",
                                    "analyzedPeriod": {
                                      "startDate": "2025-01-01",
                                      "endDate": "2025-01-07"
                                    },
                                    "diaryCount": 5,
                                    "emotionAnalysis": {
                                      "overallMood": "우울감",
                                      "moodScore": 3.2,
                                      "dominantEmotions": ["sadness", "fatigue", "apathy"],
                                      "riskLevel": "medium",
                                      "summary": "지난 주 동안 다소 우울한 기분이 지속되었습니다..."
                                    },
                                    "recommendations": [
                                      {
                                        "type": "walking",
                                        "title": "가벼운 산책",
                                        "description": "10-15분 정도 집 근처를 천천히 걸어보세요.",
                                        "duration": 15,
                                        "priority": "high"
                                      }
                                    ],
                                    "createdAt": "2025-01-07T15:30:00Z",
                                    "completedAt": "2025-01-07T15:30:45Z"
                                  }
                                }
                                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "분석 결과를 찾을 수 없음",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Parameter(name = "id", description = "분석 ID", example = "67890")
    @Operation(
            summary = "특정 분석 결과 조회",
            description = "인증된 사용자의 특정 감정 분석 결과를 조회합니다."
    )
    public ResponseEntity<BaseResponse<AnalysisDetail>> getAnalysis(
            @PathVariable("id") Long id
    ) {
        log.info("특정 분석 결과 조회 요청 - ID: {}", id);

        // Mock 데이터 생성
        AnalyzedPeriod mockPeriod = new AnalyzedPeriod("2025-01-01", "2025-01-07");

        EmotionDetail mockEmotion = new EmotionDetail(
                "우울감",
                3.2,
                List.of("sadness", "fatigue", "apathy"),
                "medium",
                "지난 주 동안 다소 우울한 기분이 지속되었습니다..."
        );

        List<Recommendation> mockRecommendations = List.of(
                new Recommendation("walking", "가벼운 산책",
                        "10-15분 정도 집 근처를 천천히 걸어보세요.", 15, "high"),
                new Recommendation("meditation", "호흡 명상",
                        "5분간 깊은 호흡으로 마음을 진정시켜보세요.", 5, "medium"),
                new Recommendation("shower", "따뜻한 샤워",
                        "따뜻한 물로 몸과 마음을 이완시켜보세요.", 10, "medium"),
                new Recommendation("nap", "짧은 낮잠",
                        "20분 정도 짧은 휴식을 취해보세요.", 20, "low")
        );

        AnalysisDetail mockResponse = new AnalysisDetail(
                id,  // 요청받은 ID 그대로 사용
                "weekly",
                "completed",
                mockPeriod,
                5,
                mockEmotion,
                mockRecommendations,
                "2025-01-07T15:30:00Z",
                "2025-01-07T15:30:45Z"
        );

        BaseResponse<AnalysisDetail> response = new BaseResponse<>(true, mockResponse);

        log.info("특정 분석 결과 조회 응답 - ID: {}, 상태: {}, 기분: {}",
                id, mockResponse.status(), mockResponse.emotionAnalysis().overallMood());
        return ResponseEntity.ok(response);
    }

    /**
     * 분석 결과 목록 조회(페이징)
     * */
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "분석 결과 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AnalysisListResponse.class),
                            examples = @ExampleObject(
                                    name = "분석 결과 목록 조회 성공",
                                    summary = "분석 결과 목록 조회 성공 응답",
                                    value = """
                                {
                                  "success": true,
                                  "data": {
                                    "analyses": [
                                      {
                                        "analysisId": 67892,
                                        "analysisType": "weekly",
                                        "status": "completed",
                                        "analyzedPeriod": {
                                          "startDate": "2025-01-08",
                                          "endDate": "2025-01-14"
                                        },
                                        "diaryCount": 6,
                                        "emotionAnalysis": {
                                          "overallMood": "보통",
                                          "moodScore": 6.5,
                                          "riskLevel": "low"
                                        },
                                        "createdAt": "2025-01-14T20:00:00Z",
                                        "completedAt": "2025-01-14T20:00:35Z"
                                      },
                                      {
                                        "analysisId": 67889,
                                        "analysisType": "daily",
                                        "status": "processing",
                                        "analyzedPeriod": {
                                          "startDate": "2025-01-06",
                                          "endDate": "2025-01-06"
                                        },
                                        "diaryCount": 1,
                                        "emotionAnalysis": null,
                                        "createdAt": "2025-01-06T22:15:00Z",
                                        "completedAt": null
                                      }
                                    ],
                                    "pagination": {
                                      "currentPage": 1,
                                      "totalPages": 3,
                                      "totalCount": 45,
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
            @Parameter(name = "status", description = "분석 상태 필터 (processing, completed, failed)", example = "completed"),
            @Parameter(name = "analysisType", description = "분석 타입 필터 (daily, weekly, monthly)", example = "weekly")
    })
    @Operation(
            summary = "내 분석 결과 목록 조회",
            description = "인증된 사용자의 감정 분석 결과 목록을 페이징으로 조회합니다. 상태와 분석 타입으로 필터링 가능합니다."
    )
    public ResponseEntity<BaseResponse<AnalysisListResponse>> getAnalysisList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String analysisType
    ) {
        log.info("분석 결과 목록 조회 요청 - 페이지: {}, 크기: {}, 상태: {}, 타입: {}",
                page, size, status, analysisType);

        // Mock 데이터 생성
        List<AnalysisListItem> mockAnalyses = List.of(
                new AnalysisListItem(
                        67892L,
                        "weekly",
                        "completed",
                        new AnalyzedPeriod("2025-01-08", "2025-01-14"),
                        6,
                        new EmotionSummary("보통", 6.5, "low"),
                        "2025-01-14T20:00:00Z",
                        "2025-01-14T20:00:35Z"
                ),
                new AnalysisListItem(
                        67890L,
                        "weekly",
                        "completed",
                        new AnalyzedPeriod("2025-01-01", "2025-01-07"),
                        5,
                        new EmotionSummary("우울감", 3.2, "medium"),
                        "2025-01-07T15:30:00Z",
                        "2025-01-07T15:30:45Z"
                ),
                new AnalysisListItem(
                        67889L,
                        "daily",
                        "processing",
                        new AnalyzedPeriod("2025-01-06", "2025-01-06"),
                        1,
                        null,  // processing 상태라 감정 분석 결과 없음
                        "2025-01-06T22:15:00Z",
                        null   // 아직 완료되지 않음
                )
        );

        PaginationInfo mockPagination = new PaginationInfo(
                page,           // currentPage
                3,              // totalPages
                45L,            // totalCount
                page < 3,       // hasNext
                page > 1        // hasPrevious
        );

        AnalysisListResponse mockResponse = new AnalysisListResponse(mockAnalyses, mockPagination);
        BaseResponse<AnalysisListResponse> response = new BaseResponse<>(true, mockResponse);

        log.info("분석 결과 목록 조회 응답: 분석 수 {}, 페이지 {}/{}",
                mockAnalyses.size(), page, mockPagination.totalPages());
        return ResponseEntity.ok(response);
    }

    /**
     * 최신 분석 결과 조회
     * */
    @GetMapping("/latest")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "최신 분석 결과 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AnalysisDetail.class),
                            examples = @ExampleObject(
                                    name = "최신 분석 결과 조회 성공",
                                    summary = "최신 완료된 분석 결과 응답",
                                    value = """
                                {
                                  "success": true,
                                  "data": {
                                    "analysisId": 67892,
                                    "analysisType": "weekly",
                                    "status": "completed",
                                    "analyzedPeriod": {
                                      "startDate": "2025-01-08",
                                      "endDate": "2025-01-14"
                                    },
                                    "diaryCount": 6,
                                    "emotionAnalysis": {
                                      "overallMood": "보통",
                                      "moodScore": 6.5,
                                      "dominantEmotions": ["calm", "neutral", "content"],
                                      "riskLevel": "low",
                                      "summary": "이번 주는 전반적으로 안정적인 감정 상태를 보이고 있습니다."
                                    },
                                    "recommendations": [
                                      {
                                        "type": "walking",
                                        "title": "꾸준한 산책",
                                        "description": "좋은 컨디션을 유지하기 위해 매일 15분씩 산책해보세요.",
                                        "duration": 15,
                                        "priority": "medium"
                                      },
                                      {
                                        "type": "meditation",
                                        "title": "감사 명상",
                                        "description": "현재의 안정감을 더욱 깊이 느껴보는 시간을 가져보세요.",
                                        "duration": 10,
                                        "priority": "low"
                                      }
                                    ],
                                    "createdAt": "2025-01-14T20:00:00Z",
                                    "completedAt": "2025-01-14T20:00:35Z"
                                  }
                                }
                                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "완료된 분석 결과를 찾을 수 없음",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Operation(
            summary = "최신 분석 결과 조회",
            description = "인증된 사용자의 가장 최신 완료된 감정 분석 결과를 조회합니다."
    )
    public ResponseEntity<BaseResponse<AnalysisDetail>> getLatestAnalysis() {
        log.info("최신 분석 결과 조회 요청");

        // Mock 데이터 생성 - 최신 완료된 분석 결과
        AnalyzedPeriod mockPeriod = new AnalyzedPeriod("2025-01-08", "2025-01-14");

        EmotionDetail mockEmotion = new EmotionDetail(
                "보통",
                6.5,
                List.of("calm", "neutral", "content"),
                "low",
                "이번 주는 전반적으로 안정적인 감정 상태를 보이고 있습니다."
        );

        List<Recommendation> mockRecommendations = List.of(
                new Recommendation(
                        "walking", "꾸준한 산책",
                        "좋은 컨디션을 유지하기 위해 매일 15분씩 산책해보세요.", 15, "medium"
                ),
                new Recommendation(
                        "meditation", "감사 명상",
                        "현재의 안정감을 더욱 깊이 느껴보는 시간을 가져보세요.", 10, "low"
                )
        );

        AnalysisDetail mockResponse = new AnalysisDetail(
                67892L,  // 최신 분석 ID
                "weekly",
                "completed",
                mockPeriod,
                6,
                mockEmotion,
                mockRecommendations,
                "2025-01-14T20:00:00Z",
                "2025-01-14T20:00:35Z"
        );

        BaseResponse<AnalysisDetail> response = new BaseResponse<>(true, mockResponse);

        log.info("최신 분석 결과 조회 응답 - ID: {}, 상태: {}, 기분: {}",
                mockResponse.analysisId(), mockResponse.status(), mockResponse.emotionAnalysis().overallMood());
        return ResponseEntity.ok(response);
    }

    /**
     * 분석 결과 피드백 제출
     * */
    @PostMapping("/{id}/feedback")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "피드백 제출 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(
                                    name = "피드백 제출 성공",
                                    summary = "피드백 제출 성공 응답",
                                    value = """
                                {
                                  "success": true,
                                  "message": "피드백이 제출되었습니다. 더 나은 분석을 위해 활용하겠습니다."
                                }
                                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "분석 결과를 찾을 수 없음",
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
    @Parameter(name = "id", description = "분석 ID", example = "67890")
    @Operation(
            summary = "분석 결과 피드백 제출",
            description = "인증된 사용자가 특정 분석 결과에 대한 피드백을 제출합니다. AI 학습 개선에 활용됩니다."
    )
    public ResponseEntity<BaseResponse<String>> submitFeedback(
            @PathVariable("id") Long id,
            @RequestBody @Valid AnalysisFeedbackRequest request
    ) {
        log.info("분석 결과 피드백 제출 요청 - 분석 ID: {}, 정확성: {}, 피드백 타입: {}",
                id, request.isAccurate(), request.feedbackType());

        // Mock 응답 - 피드백 접수 완료 메시지
        String responseMessage = "피드백이 제출되었습니다. 더 나은 분석을 위해 활용하겠습니다.";
        BaseResponse<String> response = new BaseResponse<>(true, responseMessage);

        log.info("분석 결과 피드백 제출 응답 - 분석 ID: {}, 메시지: {}", id, responseMessage);
        return ResponseEntity.ok(response);
    }
}