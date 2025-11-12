package hansung.hansung_connect.domain.link.controller;

import hansung.hansung_connect.auth.token.JwtAuthFilter;
import hansung.hansung_connect.common.response.ApiResponse;
import hansung.hansung_connect.domain.link.dto.LinkRequestDTO;
import hansung.hansung_connect.domain.link.dto.LinkResponseDTO;
import hansung.hansung_connect.domain.link.service.LinkCommandService;
import hansung.hansung_connect.domain.link.service.LinkQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Link", description = "링크 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/links")
public class LinkController {

    private final LinkCommandService linkCommandService;
    private final LinkQueryService linkQueryService;

    @Operation(
            summary = "링크 추가",
            description = """
                    새로운 링크를 추가합니다.  
                    <br><br>
                    요청 규칙  
                    - type: 링크 종류 (LINKEDIN, INSTAGRAM, GITHUB, NOTION, GOOGLE_DRIVE)  
                    - url: 유효한 링크 주소 (예: https://github.com/username)  
                    <br>
                    """
    )
    @PostMapping
    public ResponseEntity<LinkResponseDTO.LinkResultDTO> createLink(
            @Parameter(hidden = true) @AuthenticationPrincipal JwtAuthFilter.SimpleUserPrincipal me,
            @Valid @RequestBody LinkRequestDTO.CreateLinkDTO request
    ) {
        LinkResponseDTO.LinkResultDTO result = linkCommandService.createLink(me.id(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(
            summary = "링크 수정(전체 대체, PUT)",
            description = """
                    기존 링크를 전체 대체 방식으로 수정합니다.  
                    <br><br>
                    요청 규칙  
                    - path variable linkId: 수정할 링크의 ID  
                    - type: 링크 종류 (LINKEDIN, INSTAGRAM, GITHUB, NOTION, GOOGLE_DRIVE) 
                    <br><br>  
                    
                    제약 사항  
                    - 본인 소유 링크만 수정 가능  
                    - 동일 유저 내 동일 타입은 중복 불가  
                    """
    )
    @PutMapping("/{linkId}")
    public ResponseEntity<LinkResponseDTO.LinkResultDTO> updateLink(
            @Parameter(hidden = true) @AuthenticationPrincipal JwtAuthFilter.SimpleUserPrincipal me,
            @PathVariable Long linkId,
            @Valid @RequestBody LinkRequestDTO.UpdateLinkDTO request
    ) {
        LinkResponseDTO.LinkResultDTO result = linkCommandService.updateLink(me.id(), linkId, request);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "외부링크 일괄 추가",
            description = """
                    여러 개의 외부 링크를 한 번에 추가합니다.
                    - type: 링크 종류 (LINKEDIN, INSTAGRAM, GITHUB, NOTION, GOOGLE_DRIVE)  
                    - url: 유효한 링크 주소 (예: https://github.com/username)  
                    """
    )
    @PostMapping("/batch")
    public ApiResponse<LinkResponseDTO.LinkResultListDTO> createLinksBatch(
            @Parameter(hidden = true) @AuthenticationPrincipal JwtAuthFilter.SimpleUserPrincipal me,
            @Valid @RequestBody LinkRequestDTO.CreateLinksDTO request
    ) {
        return ApiResponse.onSuccess(linkCommandService.createLinks(me.id(), request));
    }

    @Operation(
            summary = "외부링크 단건 조회",
            description = """
                    링크 ID로 단건 조회합니다.  
                    - userId와 무관하게 누구나 조회 가능합니다.  
                    - 응답에는 id, type, url이 포함됩니다.
                    """
    )
    @GetMapping("/{linkId}")
    public ApiResponse<LinkResponseDTO.LinkResultDTO> getLinkById(
            @Parameter(description = "조회할 링크 ID") @PathVariable Long linkId
    ) {
        return ApiResponse.onSuccess(linkQueryService.getLink(linkId));
    }

    @Operation(
            summary = "내 외부링크 전체 조회",
            description = """
                    현재 로그인 사용자의 모든 외부링크를 조회합니다.
                    """
    )
    @GetMapping("/mylinks")
    public ApiResponse<LinkResponseDTO.LinkResultListDTO> getMyLinks(
            @Parameter(hidden = true) @AuthenticationPrincipal JwtAuthFilter.SimpleUserPrincipal me
    ) {
        return ApiResponse.onSuccess(linkQueryService.getMyLinks(me.id()));
    }
}
