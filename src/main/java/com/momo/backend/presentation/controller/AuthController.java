package com.momo.backend.presentation.controller;

import com.momo.backend.common.dto.BaseResponse;
import com.momo.backend.presentation.dto.request.KakaoLoginRequest;
import com.momo.backend.presentation.dto.request.RefreshTokenRequest;
import com.momo.backend.presentation.dto.request.UserWithdrawRequest;
import com.momo.backend.presentation.dto.response.KakaoLoginResponse;
import com.momo.backend.presentation.dto.response.RefreshTokenResponse;
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

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Tag(name = "인증 컨트롤러", description = "인증 컨트롤러에 대한 API입니다.")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    /**
     * 카카오 소셜 로그인
     * */
    @PostMapping("/kakao/login")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = KakaoLoginResponse.class)
                    )
            )})
    @Parameters({
            @Parameter(name = "authorizationCode", description = "인가 코드", example = "abc123def456")
    })
    @Operation(summary = "카카오 로그인", description = "authorizationCode 가 필요합니다.")
    public ResponseEntity<BaseResponse<KakaoLoginResponse>> kakaoLogin(
            @RequestBody @Valid KakaoLoginRequest request
    ) {
        log.info("카카오 로그인 요청: {}", request);

        // Mock 데이터 생성
        KakaoLoginResponse.UserInfo mockUser = new KakaoLoginResponse.UserInfo(
                12345L,
                "987654321",
                "모모유저",
                null,  // 이메일 동의 안함
                null,  // 폰번호 동의 안함
                true,  // 신규 유저
                List.of("profile_nickname", "profile_image")
        );

        KakaoLoginResponse mockResponse = new KakaoLoginResponse(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.mock_access_token_data.signature",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.mock_refresh_token_data.signature",
                mockUser
        );

        BaseResponse<KakaoLoginResponse> response = new BaseResponse<>(true, mockResponse);

        log.info("카카오 로그인 응답: {}", response);
        return ResponseEntity.ok(response);
    }

    /**
     * 토큰 갱신
     * */
    @PostMapping("/refresh")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "토큰 갱신 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RefreshTokenResponse.class)
                    )
            )})
    @Parameters({
            @Parameter(
                    name = "Authorization",
                    description = "Bearer + accessToken",
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                    in = HEADER,
                    required = true
            ),
            @Parameter(name = "refreshToken",
                    description = "리프레시 토큰",
                    example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    })
    @Operation(
            summary = "토큰 갱신",
            description = "Authorization 헤더에 accessToken과 RequestBody에 refreshToken이 필요합니다."
    )
    public ResponseEntity<BaseResponse<RefreshTokenResponse>> refreshToken(
            @RequestBody @Valid RefreshTokenRequest request
    ) {
        log.info("토큰 갱신 요청: {}", request);

        // Mock 데이터 생성 - 새로운 토큰들 반환
        RefreshTokenResponse mockResponse = new RefreshTokenResponse(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.new_access_token_" + System.currentTimeMillis() + ".signature",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.new_refresh_token_" + System.currentTimeMillis() + ".signature"
        );

        BaseResponse<RefreshTokenResponse> response = new BaseResponse<>(true, mockResponse);

        log.info("토큰 갱신 응답: {}", response);
        return ResponseEntity.ok(response);
    }
}
