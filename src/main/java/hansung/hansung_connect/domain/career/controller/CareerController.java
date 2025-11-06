package hansung.hansung_connect.domain.career.controller;

import hansung.hansung_connect.common.response.ApiResponse;
import hansung.hansung_connect.domain.career.dto.CareerRequestDTO;
import hansung.hansung_connect.domain.career.dto.CareerRequestDTO.BatchCreateRequestDTO;
import hansung.hansung_connect.domain.career.dto.CareerResponseDTO;
import hansung.hansung_connect.domain.career.service.CareerCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Career", description = "커리어 관련 API")
public class CareerController {

    private final CareerCommandService careerCommandService;

    @Operation(
            summary = "내 커리어 1개 추가",
            description = """
                    커리어 하나씩 추가하는 API 입니다. <br>
                    startYm과 endYm은 커리어 시작연월, 종료연월입니다.<br>
                    - 형식은 "2024-03" 혹은 "2024.03" (-),(.) 둘다 사용가능합니다.
                    - 만약 현재 재직중(isEmployed == true)이라면 endYm : null 로 입력해야 합니다.<br>
                    
                    JobType ENUM
                    - PERMANENT //정규직
                    - TEMPORARY //계약직
                    - INTERN //인턴
                    - FREELANCER //프리랜서
                    """
    )
    @PostMapping("/users/me/careers")
    public ApiResponse<CareerResponseDTO.CreateResponseDTO> createCareer(
            @RequestBody CareerRequestDTO.CreateRequestDTO requestDTO) {

        CareerResponseDTO.CreateResponseDTO response = careerCommandService.createCareer(requestDTO);
        return ApiResponse.onSuccess(response);
    }

    @Operation(
            summary = "내 커리어 여러 개 일괄 추가",
            description = """
                    온보딩 등에서 커리어를 **여러 개 한 번에 등록**하는 API입니다.<br>
                    - items 배열에 단건 생성 스펙과 동일한 객체들을 담아 전송합니다.<br>
                    - startYm, endYm 형식: "yyyy-MM" 또는 "yyyy.MM" (둘 다 허용)<br>
                    - 재직 중(employed == true)이면 endYm은 null이어야 합니다.<br><br>
                    <b>JobType ENUM</b><br>
                    • PERMANENT (정규직)<br>
                    • TEMPORARY (계약직)<br>
                    • INTERN (인턴)<br>
                    • FREELANCER (프리랜서)
                    """
    )
    @PostMapping("/users/me/careers/batch")
    public ApiResponse<CareerResponseDTO.BulkCreateResponseDTO> createCareers(
            @RequestBody BatchCreateRequestDTO requestDTO) {
        return ApiResponse.onSuccess(careerCommandService.createCareers(requestDTO));
    }
}

