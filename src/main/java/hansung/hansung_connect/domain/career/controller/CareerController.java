package hansung.hansung_connect.domain.career.controller;

import hansung.hansung_connect.common.response.ApiResponse;
import hansung.hansung_connect.domain.career.dto.CareerRequestDTO;
import hansung.hansung_connect.domain.career.dto.CareerRequestDTO.BatchCreateRequestDTO;
import hansung.hansung_connect.domain.career.dto.CareerResponseDTO;
import hansung.hansung_connect.domain.career.dto.CareerResponseDTO.CreateResponseDTO;
import hansung.hansung_connect.domain.career.service.CareerCommandService;
import hansung.hansung_connect.domain.career.service.CareerQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Career", description = "커리어 관련 API")
public class CareerController {

    private final CareerCommandService careerCommandService;
    private final CareerQueryService careerQueryService;

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

    @Operation(
            summary = "내 커리어 수정",
            description = """
                    기존 커리어 정보를 수정(전체 대체)하는 API입니다.
                    <br><br>
                    요청 규칙
                    - careerId: 수정할 커리어의 ID (PathVariable로 전달)
                    - 요청 본문은 생성(Create) 요청과 동일하며, 전달된 값으로 전부 교체됩니다.
                    - isEmployed(재직 여부)가 true이면 endYm은 null이어야 합니다.
                    - isEmployed(재직 여부)가 false이면 endYm은 필수입니다.
                    - 연월은 "2024-04" 또는 "2024.04" 형태 모두 허용합니다.
                    
                    JobType ENUM
                    - PERMANENT (정규직)
                    - TEMPORARY (계약직)
                    - INTERN (인턴)
                    - FREELANCER (프리랜서)
                    """
    )
    @PutMapping("/users/me/careers/{careerId}")
    public ApiResponse<CareerResponseDTO.UpdateResponseDTO> updateCareer(
            @PathVariable Long careerId,
            @RequestBody CareerRequestDTO.UpdateRequestDTO request) {
        return ApiResponse.onSuccess(careerCommandService.updateCareer(careerId, request));
    }

    @Operation(
            summary = "커리어 단건 조회",
            description = """
                    커리어 ID로 단건을 조회합니다. <br>
                    사용자와 무관하게 careerId만 있으면 조회 가능합니다. <br><br>
                    응답 필드: id, companyName, position, jobType, employed, startYm, endYm
                    """
    )
    @GetMapping("/careers/{careerId}")
    public ApiResponse<CareerResponseDTO.CreateResponseDTO> getCareer(@PathVariable Long careerId) {
        return ApiResponse.onSuccess(careerQueryService.getCareer(careerId));
    }

    @Operation(
            summary = "내 커리어 전체 조회",
            description = """
                    현재 사용자(임시 userId=1L)의 모든 커리어를 조회합니다.<br>
                    """
    )
    @GetMapping("/careers/mycareers")
    public ApiResponse<List<CreateResponseDTO>> getMyCareers() {
        return ApiResponse.onSuccess(careerQueryService.getMyCareers());
    }
}

