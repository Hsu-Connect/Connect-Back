package hansung.hansung_connect.auth.controller;

import hansung.hansung_connect.auth.dto.LoginRequest;
import hansung.hansung_connect.auth.dto.LoginResponse;
import hansung.hansung_connect.auth.dto.LogoutRequest;
import hansung.hansung_connect.auth.dto.OnboardingRequest;
import hansung.hansung_connect.auth.service.AuthService;
import hansung.hansung_connect.auth.token.JwtAuthFilter.SimpleUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "카카오 로그인, 계정 관련 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "카카오 로그인",
            description = """
                    카카오 access_token 을 받아 서버 로그인을 진행합니다.
                    - 현재 provider는 "kakao"로 통일 (확장성 고려)
                    - 성공 시 한성커넥트 서비스용 JWT(access/refresh) 를 발급
                    - isNewUser, needsOnboarding으로 온보딩 필요여부 판단
                    """
    )
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return authService.loginWithKakao(req.accessToken());
    }

    @Operation(
            summary = "온보딩",
            description = """
                    JWT의 사용자 id로 사용자 식별 후, 온보딩 과정에서 받은 사용자정보를 저장합니다.
                    - academicStatus ENUM
                        - ENROLLED //재학 중
                        - GRADUATED  //졸업
                        - EXPECTED_GRADUATION    //졸업 예정
                        - COMPLETED  //수료
                        - LEAVE_OF_ABSENCE   //휴학""",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("/onboarding")
    public void onboarding(
            @Parameter(hidden = true) @AuthenticationPrincipal SimpleUserPrincipal me,
            @RequestBody OnboardingRequest req) {
        authService.onboarding(me.id(), req);
    }

    @Operation(
            summary = "로그아웃",
            description = """
                    서버에 저장된 RefreshToken을 폐기합니다.
                    - Body에 로그인 응답에서 받은 refreshToken을 전달
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("/logout")
    public void logout(
            @Parameter(hidden = true) @AuthenticationPrincipal SimpleUserPrincipal me,
            @RequestBody LogoutRequest req) {
        authService.logout(me.id(), req.refreshToken());
    }
}


