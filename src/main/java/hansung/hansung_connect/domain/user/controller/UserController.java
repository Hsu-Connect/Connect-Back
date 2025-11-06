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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Operation(
            summary = "한성인 찾기(멘토 목록 조회) (전공 우선 정렬, 15개/페이지)",
            description = """
                    로그인 사용자와 같은 전공인 멘토 우선정렬하여 보여집니다.<br><br>
                    페이징: page(기본 0), size(기본 15)<br>
                    반환 필드:
                    - totalMentorCount: 전체 멘토 수
                    - items: 멘토 카드 리스트
                    """
    )
    @GetMapping("/mentors")
    public ResponseEntity<ApiResponse<UserResponseDTO.MentorListResponse>> getMentors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size
    ) {
        // 개발 단계: 하드코딩. 추후 SecurityContext에서 꺼내기
        Long currentUserId = 1L;

        // 강제 size=15 정책 사용 시 고정
        // size = 15;

        UserResponseDTO.MentorListResponse result =
                userQueryService.getMentors(currentUserId, page, size);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @Operation(
            summary = "한성인 찾기 - 유저 프로필 조회",
            description = """
                    한성인 찾기 시 목록에서 유저를 클릭하면 나오는 유저 프로필화면입니다.<br>
                    특정 유저(멘토)의 전체 프로필을 반환합니다.<br>
                    - 기본정보: 이름, 학번 앞 두 자리, 전공, 구직여부, 재직여부, 재학상태<br>
                    - 커리어 목록(CareerItem), 링크 목록(LinkItem)
                    """
    )
    @GetMapping("/profiles/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDTO.UserProfileResponse>> getUserProfile(
            @PathVariable Long userId
    ) {
        UserResponseDTO.UserProfileResponse result = userQueryService.getUserProfile(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

}

