package com.momo.backend.presentation.controller;

import com.momo.backend.common.dto.BaseResponse;
import com.momo.backend.presentation.dto.user.request.UserWithdrawRequest;
import com.momo.backend.presentation.dto.user.response.UserInfoResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.time.LocalDateTime;
import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Tag(name = "사용자 컨트롤러", description = "사용자 정보 관리 API - 인증 필요")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    /**
     * 내 정보 조회
     * */
    @GetMapping("/me")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "내 정보 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoResponse.class),
                            examples = @ExampleObject(
                                    name = "내 정보 조회 성공",
                                    summary = "사용자 정보 조회 성공 응답",
                                    value = """
                                    {
                                      "success": true,
                                      "data": {
                                        "id": 12345,
                                        "socialId": "987654321",
                                        "nickname": "모모유저",
                                        "email": null,
                                        "phoneNumber": null,
                                        "profileImage": "https://k.kakaocdn.net/dn/profile.jpg",
                                        "createdAt": "2025-01-04T10:30:00Z",
                                        "appLockEnabled": false,
                                        "consentedScopes": ["profile_nickname", "profile_image"]
                                      }
                                    }
                                    """
                            )
                    )
            ),
    })
    @Parameters({
            @Parameter(
                    name = "Authorization",
                    description = "Bearer + accessToken",
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                    in = HEADER,
                    required = true
            )
    })
    @Operation(
            summary = "내 정보 조회",
            description = "인증된 사용자의 정보를 조회합니다."
    )
    public ResponseEntity<BaseResponse<UserInfoResponse>> getMyInfo() {
        log.info("내 정보 조회 요청");

        // Mock 데이터 생성
        UserInfoResponse mockResponse = new UserInfoResponse(
                12345L,
                "987654321",
                "모모유저",
                null,  // 이메일 동의 안함
                null,  // 폰번호 동의 안함
                "https://k.kakaocdn.net/dn/profile.jpg",
                LocalDateTime.of(2025, 1, 4, 10, 30, 0),
                false,  // 앱 잠금 비활성화
                List.of("profile_nickname", "profile_image")
        );

        BaseResponse<UserInfoResponse> response = new BaseResponse<>(true, mockResponse);

        log.info("내 정보 조회 응답: {}", response);
        return ResponseEntity.ok(response);
    }

    // 회원탈퇴
    @DeleteMapping("/me")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "회원탈퇴 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(
                                    name = "회원탈퇴 성공",
                                    summary = "회원탈퇴 성공 응답",
                                    value = """
                                    {
                                      "success": true,
                                      "data": "회원 탈퇴가 완료되었습니다"
                                    }
                                    """
                            )
                    )
            )
    })
    @Parameters({
            @Parameter(
                    name = "Authorization",
                    description = "accessToken",
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                    in = HEADER,
                    required = true
            ),
            @Parameter(name = "reason",
                    description = "탈퇴 이유",
                    example = "앱을 더이상 사용하지 않음.")
    })
    @Operation(
            summary = "회원탈퇴",
            description = "인증된 사용자의 회원탈퇴를 처리합니다. " +
                    "Authorization 헤더에 accessToken, RequestBody에 reason이 필요합니다."

    )
    public ResponseEntity<BaseResponse<String>> withdraw(
            @RequestBody @Valid UserWithdrawRequest request
    ) {
        log.info("회원탈퇴 요청 - 탈퇴 이유: {}", request.reason());

        // Mock 응답 - 단순 메시지만 반환
        BaseResponse<String> response = new BaseResponse<>(true, "회원 탈퇴가 완료되었습니다");

        log.info("회원탈퇴 응답: {}", response);
        return ResponseEntity.ok(response);
    }
}
