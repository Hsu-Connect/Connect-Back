package hansung.hansung_connect.domain.user.controller;

import hansung.hansung_connect.common.response.ApiResponse;
import hansung.hansung_connect.domain.user.dto.UserResponseDTO;
import hansung.hansung_connect.domain.user.dto.UserResponseDTO.SummaryCardResponse;
import hansung.hansung_connect.domain.user.service.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저/프로필 조회 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserQueryService userQueryService;

    @Operation(
            summary = "마이페이지 상단 사용자요약카드",
            description = """
                    마이페이지 상단 카드에 필요한 최소 사용자 정보를 반환합니다.
                    <br><br>
                    필드 정보
                    - studentNumberPrefix: 학번 앞 두 자리 (예: "21")  
                    - major: 전공  
                    - jobSeeking: 구직 여부 (구직중/구직중아님)
                    - academicStatus: 학교관련 상태 
                        - (ENROLLED:재학중/GRADUATED:졸업/EXPECTED_GRADUATION:졸업예정/COMPLETED:수료/LEAVE_OF_ABSENCE:휴학)
                    - employed: 현재 재직상태 (재직중/재직중아님)
                    """
    )
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<SummaryCardResponse>> getMySummaryCard() {
        // TODO: 실제 배포 시 SecurityContext에서 userId 추출
        Long currentUserId = 1L;

        UserResponseDTO.SummaryCardResponse result =
                userQueryService.getMySummaryCard(currentUserId);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }
}

