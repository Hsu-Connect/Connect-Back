package hansung.hansung_connect.test;

import hansung.hansung_connect.common.response.ApiResponse;
import hansung.hansung_connect.test.dto.TestResponse;
import hansung.hansung_connect.test.dto.TestResponse.TestDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("")
    public ApiResponse<TestDTO> test() {
        return ApiResponse.onSuccess(TestConverter.toTempTestDTO());
    }
}
