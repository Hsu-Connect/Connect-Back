package hansung.hansung_connect.domain.user.controller;

import hansung.hansung_connect.common.response.ApiResponse;
import hansung.hansung_connect.domain.user.converter.UserConverter;
import hansung.hansung_connect.domain.user.dto.UserRequestDTO;
import hansung.hansung_connect.domain.user.dto.UserResponseDTO;
import hansung.hansung_connect.domain.user.dto.UserResponseDTO.SummaryCardResponse;
import hansung.hansung_connect.domain.user.service.UserCommandService;
import hansung.hansung_connect.domain.user.service.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저/프로필 조회 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    private final UserConverter userConverter;

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

    @Operation(
            summary = "내 프로필 수정 화면(폼 채움용 기존정보)",
            description = """
                    프로필 수정 화면에 필요한 모든 기존값을 한 번에 반환합니다.
                    - 기본정보: 학번, 이름, 전공, 멘토여부, 구직여부, 재학상태
                    - 커리어 전체: id, companyName, position, jobType, employed, startYm, endYm
                    - 외부 링크 전체: id, type, url
                    """
    )
    @GetMapping("/myprofile")
    public ResponseEntity<ApiResponse<UserResponseDTO.MyProfileResponse>> getMyProfile() {
        Long currentUserId = 1L; // TODO: 인증 연동 시 교체
        return ResponseEntity.ok(
                ApiResponse.onSuccess(userQueryService.getMyProfile(currentUserId))
        );
    }

    @Operation(
            summary = "내 프로필(기본 정보) 부분수정",
            description = """
                    학번, 이름, 전공, 멘토참여여부, 구직여부만 부분 수정합니다.
                    <br><br>
                    - PATCH 이므로 전달된 필드만 반영합니다(null은 무시)  
                    - 커리어/외부링크는 각 전용 API로 수정하세요.
                    """
    )
    @PatchMapping("/myprofile")
    public ResponseEntity<ApiResponse<Void>> updateMyBasicProfile(
            @Valid @RequestBody UserRequestDTO.UpdateBasicProfileRequest request
    ) {
        Long currentUserId = 1L; // TODO: 인증 연동 후 SecurityContext에서 추출

        // 입력 정규화(공백 제거 등)
        UserRequestDTO.UpdateBasicProfileRequest normalized = userConverter.normalize(request);

        // TODO: 실제 업데이트는 Command 서비스에서 처리 (예시)
        userCommandService.updateMyBasicProfile(currentUserId, normalized);

        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }
}

