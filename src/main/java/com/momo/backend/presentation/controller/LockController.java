package com.momo.backend.presentation.controller;

import com.momo.backend.common.dto.BaseResponse;
import com.momo.backend.presentation.dto.lock.request.LockCreateRequest;
import com.momo.backend.presentation.dto.lock.request.LockUnlockRequest;
import com.momo.backend.presentation.dto.lock.request.LockUpdateRequest;
import com.momo.backend.presentation.dto.lock.response.LockCreateResponse;
import com.momo.backend.presentation.dto.lock.response.LockResponse;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "앱 잠금 컨트롤러", description = "앱 잠금 관련 API - 인증 필요")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/lock")
@RestController
public class LockController {

    /**
     * 앱 잠금 설정 (최초 생성)
     */
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "앱 잠금 설정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LockCreateResponse.class),
                            examples = @ExampleObject(
                                    name = "앱 잠금 설정 성공",
                                    summary = "앱 잠금 설정 성공 응답",
                                    value = """
                                            {
                                              "success": true,
                                              "data": {
                                                "lockId": 12345,
                                                "isEnabled": true,
                                                "useBiometric": false,
                                                "createdAt": "2025-01-07T15:30:00Z"
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (비밀번호 불일치, 유효성 검증 실패)",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 잠금 설정이 존재함",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Operation(
            summary = "앱 잠금 설정",
            description = "인증된 사용자의 앱 잠금을 최초로 설정합니다. 6자리 숫자 비밀번호를 설정합니다."
    )
    public ResponseEntity<BaseResponse<LockCreateResponse>> createLock(
            @RequestBody @Valid LockCreateRequest request
    ) {
        log.info("앱 잠금 생성 요청 - 6자리 비밀번호 설정");

        // 비밀번호 확인 검증
        if (!request.password().equals(request.confirmPassword())) {
            log.warn("앱 잠금 생성 실패 - 비밀번호 불일치");
            // 실제로는 예외 처리하지만 Mock에서는 로그만
        }

        // Mock 데이터 생성
        LockCreateResponse mockResponse = new LockCreateResponse(
                12345L,
                true,   // 잠금 활성화됨
                false,  // MVP에서는 생체인증 비활성화
                "2025-01-07T15:30:00Z"
        );

        BaseResponse<LockCreateResponse> response = new BaseResponse<>(true, mockResponse);

        log.info("앱 잠금 생성 응답 - 잠금 ID: {}", mockResponse.lockId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 앱 잠금 비밀번호 변경
     */
    @PutMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "앱 잠금 비밀번호 변경 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LockResponse.class),
                            examples = @ExampleObject(
                                    name = "앱 잠금 비밀번호 변경 성공",
                                    summary = "앱 잠금 비밀번호 변경 성공 응답",
                                    value = """
                                            {
                                              "success": true,
                                              "data": {
                                                "lockId": 12345,
                                                "isEnabled": true,
                                                "useBiometric": false,
                                                "createdAt": "2025-01-07T15:30:00Z",
                                                "updatedAt": "2025-01-08T16:48:00Z"
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (비밀번호 불일치, 유효성 검증 실패)",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "앱 잠금 설정이 존재하지 않음",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Operation(
            summary = "앱 잠금 비밀번호 변경",
            description = "인증된 사용자의 앱 잠금 6자리 비밀번호를 변경합니다."
    )
    public ResponseEntity<BaseResponse<LockResponse>> updateLock(
            @RequestBody @Valid LockUpdateRequest request
    ) {
        log.info("앱 잠금 비밀번호 변경 요청 - 기존 비밀번호: {}, 새 비밀번호: {}", request.oldPassword(), request.newPassword());

        // 비밀번호 확인 검증 (실제 서비스에서는 기존 비밀번호 일치 여부도 검증)
        if (!request.newPassword().equals(request.confirmPassword())) {
            log.warn("앱 잠금 비밀번호 변경 실패 - 새 비밀번호 불일치");
            // 실제로는 예외 처리하지만 Mock에서는 로그만
        }

        // Mock 데이터 생성
        LockResponse mockResponse = new LockResponse(
                12345L,
                true,   // 잠금 활성화됨
                false,  // MVP에서는 생체인증 비활성화
                "2025-01-07T15:30:00Z", // 최초 생성일
                "2025-01-08T16:48:00Z"  // 변경일(업데이트)
        );

        BaseResponse<LockResponse> response = new BaseResponse<>(true, mockResponse);

        log.info("앱 잠금 비밀번호 변경 응답 - 잠금 ID: {}", mockResponse.lockId());
        return ResponseEntity.ok(response);
    }

    /**
     * 앱 잠금 해제
     */
    @PostMapping("/unlock")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "앱 잠금 해제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LockResponse.class),
                            examples = @ExampleObject(
                                    name = "앱 잠금 해제 성공",
                                    summary = "앱 잠금 해제 성공 응답",
                                    value = """
                                            {
                                              "success": true,
                                              "data": {
                                                "lockId": 12345,
                                                "isEnabled": false,
                                                "useBiometric": false,
                                                "createdAt": "2025-01-07T15:30:00Z",
                                                "updatedAt": "2025-01-09T10:01:00Z"
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (비밀번호 불일치, 유효성 검증 실패)",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "앱 잠금 설정이 존재하지 않음",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Operation(
            summary = "앱 잠금 해제",
            description = "인증된 사용자의 앱 잠금을 해제(비활성화)합니다."
    )
    // todo 리턴 값을 분리하던가 LockResponse 로 변경해서 재사용
    public ResponseEntity<BaseResponse<LockResponse>> unlockLock(
            @RequestBody @Valid LockUnlockRequest request
    ) {
        log.info("앱 잠금 해제 요청 - 입력 비밀번호: {}", request.password());

        // 실제 서비스에서는 비밀번호 검증 및 상태 변경 로직 필요
        // Mock에서는 성공 응답만 반환

        LockResponse mockResponse = new LockResponse(
                12345L,
                false,  // 잠금 해제됨 (isEnabled: false)
                false,  // MVP에서는 생체인증 비활성화
                "2025-01-07T15:30:00Z", // 최초 생성일
                "2025-01-09T10:01:00Z"  // 해제(업데이트)일
        );

        BaseResponse<LockResponse> response = new BaseResponse<>(true, mockResponse);

        log.info("앱 잠금 해제 응답 - 잠금 ID: {}", mockResponse.lockId());
        return ResponseEntity.ok(response);
    }

    /**
     * 앱 잠금 상태 조회
     */
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "앱 잠금 상태 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LockResponse.class),
                            examples = @ExampleObject(
                                    name = "앱 잠금 상태 조회 성공",
                                    summary = "앱 잠금 상태 조회 성공 응답",
                                    value = """
                                            {
                                              "success": true,
                                              "data": {
                                                "lockId": 12345,
                                                "isEnabled": true,
                                                "useBiometric": false,
                                                "createdAt": "2025-01-07T15:30:00Z",
                                                "updatedAt": "2025-01-08T16:48:00Z"
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "앱 잠금 설정이 존재하지 않음",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Operation(
            summary = "앱 잠금 상태 조회",
            description = "인증된 사용자의 앱 잠금 상태 정보를 조회합니다."
    )
    public ResponseEntity<BaseResponse<LockResponse>> getLockStatus() {
        // Mock 데이터 예시
        LockResponse mockResponse = new LockResponse(
                12345L,
                true,
                false,
                "2025-01-07T15:30:00Z",
                "2025-01-08T16:48:00Z"
        );
        BaseResponse<LockResponse> response = new BaseResponse<>(true, mockResponse);
        return ResponseEntity.ok(response);
    }

    /** 앱 잠금 완전 삭제 */
    @DeleteMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "앱 잠금 삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(
                                    name = "앱 잠금 삭제 성공",
                                    summary = "앱 잠금 삭제 성공 응답",
                                    value = """
                                    {
                                      "success": true,
                                      "message": "앱 잠금을 삭제했습니다."
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "앱 잠금 설정이 존재하지 않음",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Operation(
            summary = "앱 잠금 완전 삭제",
            description = "인증된 사용자의 앱 잠금 설정을 완전히 삭제(해제)합니다."
    )
    public ResponseEntity<BaseResponse<String>> deleteLock() {
        // 실제 삭제 로직은 추후 구현(하드/소프트 딜리트)
        BaseResponse<String> response = new BaseResponse<>(true, "앱 잠금을 삭제했습니다.");
        return ResponseEntity.ok(response);
    }
}