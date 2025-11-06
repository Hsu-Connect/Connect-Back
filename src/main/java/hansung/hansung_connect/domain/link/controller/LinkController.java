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
import org.springframework.web.bind.annotation.PostMapping;
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
}
