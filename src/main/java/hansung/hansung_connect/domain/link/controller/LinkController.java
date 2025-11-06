package hansung.hansung_connect.domain.link.controller;

import hansung.hansung_connect.domain.link.dto.LinkRequestDTO;
import hansung.hansung_connect.domain.link.dto.LinkResponseDTO;
import hansung.hansung_connect.domain.link.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final LinkService linkService;

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
            @Valid @RequestBody LinkRequestDTO.CreateLinkDTO request
    ) {
        // 현재 개발 단계이므로 userId 고정
        Long userId = 1L;

        LinkResponseDTO.LinkResultDTO result = linkService.createLink(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(
            summary = "링크 수정(전체 대체, PUT)",
            description = """
                    기존 링크를 전체 대체 방식으로 수정합니다.  
                    <br><br>
                    요청 규칙  
                    - path variable linkId: 수정할 링크의 ID  
                    - body: type, url 필수  
                    <br><br>
                    제약 사항  
                    - 본인 소유 링크만 수정 가능  
                    - 동일 유저 내 동일 타입은 중복 불가  
                    """
    )
    @PutMapping("/{linkId}")
    public ResponseEntity<LinkResponseDTO.LinkResultDTO> updateLink(
            @PathVariable Long linkId,
            @Valid @RequestBody LinkRequestDTO.UpdateLinkDTO request
    ) {
        Long userId = 1L; // 개발 단계라 userId 고정
        LinkResponseDTO.LinkResultDTO result = linkService.updateLink(userId, linkId, request);
        return ResponseEntity.ok(result);
    }
}
