package hansung.hansung_connect.test;

import hansung.hansung_connect.common.response.ApiResponse;
import hansung.hansung_connect.test.dto.TestResponse;
import hansung.hansung_connect.test.dto.TestResponse.TestDTO;
import hansung.hansung_connect.test.service.TestQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Test", description = "테스트용 API")
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
    public ApiResponse<TestResponse.ExceptionDTO> exceptionAPI(@RequestParam Integer flag){
        testQueryService.CheckFlag(flag);
        return ApiResponse.onSuccess(TestConverter.toExceptionDTO(flag));
    }
}
