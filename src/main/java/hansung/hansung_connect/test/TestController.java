package hansung.hansung_connect.test;

import hansung.hansung_connect.auth.kakao.KakaoClient;
import hansung.hansung_connect.auth.kakao.KakaoUserDto;
import hansung.hansung_connect.common.response.ApiResponse;
import hansung.hansung_connect.test.dto.TestResponse;
import hansung.hansung_connect.test.dto.TestResponse.TestDTO;
import hansung.hansung_connect.test.service.TestQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Test", description = "테스트용 API")
// @Profile({"local", "dev"}) // 개발 완료 시 이 줄 활성화
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestQueryService testQueryService;

    @GetMapping("")
    public ApiResponse<TestDTO> test() {
        return ApiResponse.onSuccess(TestConverter.toTempTestDTO());
    }

    @Operation(
            summary = "에러 핸들링 테스트",
            description = "Query String으로 전달된 flag 값이 1일 경우 예외를 발생시키는 테스트용 API입니다."
    )
    @GetMapping("/exception")
    public ApiResponse<TestResponse.ExceptionDTO> exceptionAPI(@RequestParam Integer flag) {
        testQueryService.CheckFlag(flag);
        return ApiResponse.onSuccess(TestConverter.toExceptionDTO(flag));
    }

    @Operation(
            summary = "카카오 인가 코드 콜백 (테스트)",
            description = """
                    카카오 로그인 승인 후 카카오가 리다이렉트하는 콜백 엔드포인트입니다.
                    프런트/로컬 테스트용으로 code 값을 눈으로 확인할 때만 사용하세요.
                    실제 서비스 로그인 플로우에서는 사용하지 않습니다.
                    """
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "인가 코드가 정상적으로 수신됨")
    @GetMapping("kakao/callback")
    public ResponseEntity<String> callback(
            @Parameter(
                    description = "카카오가 전달하는 인가 코드(authorization code)",
                    example = "IABcdEFgH-12xyZ......"
            )
            @RequestParam("code") String code
    ) {
        return ResponseEntity.ok("code=" + code); // 인가 코드 확인용
    }

    private final KakaoClient kakaoClient;

    @Operation(
            summary = "카카오 /v2/user/me 디버그 호출",
            description = """
                    프런트(또는 카카오 테스트 페이지)에서 받은 카카오 access_token으로
                    카카오 사용자 정보를 직접 조회해보는 디버그용 API입니다.
                    - 운영 환경에서는 노출/사용 금지
                    - 정상 토큰인지, 어떤 필드가 넘어오는지 빠르게 검증할 때 사용
                    """
    )
    @PostMapping("/kakao/me")
    public KakaoUserDto debugKakaoMe(@org.springframework.web.bind.annotation.RequestBody Map<String, String> body) {
        String accessToken = body.get("accessToken");
        return kakaoClient.getUser(accessToken);
    }
}
