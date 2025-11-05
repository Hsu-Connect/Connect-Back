package hansung.hansung_connect.domain.commnet.controller;

import hansung.hansung_connect.common.response.ApiResponse;
import hansung.hansung_connect.domain.commnet.dto.CommentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Comment", description = "댓글 관련 API")
@RestController
@RequiredArgsConstructor
public class CommentController {

    @Operation(
            summary = "댓글 작성",
            description = "댓글을 작성하는 API입니다. "
    )
    @PostMapping("/posts/{postId}/comments")
    public ApiResponse<CommentResponseDto.CommentCreateResponse> createComment(
            @Parameter(description = "게시글 아이디", example = "1")
            @PathVariable("postId") Long postId
    ) {
        Long userId = 1L;
        return ApiResponse.onSuccess(null);
    }

    @Operation(
            summary = "내 댓글 리스트 조회",
            description = """
            작성한 댓글의 리스트를 조회하는 API입니다.
            
            한 페이지에 댓글의 수는 20입니다.
            """
    )
    @GetMapping("/comments/my")
    public ApiResponse<CommentResponseDto.CommentListResponse> getMyComments(
            @Parameter(description = "페이지 번호")
            @RequestParam(defaultValue = "0") int page
    ) {
        Long userId = 1L;
        return ApiResponse.onSuccess(null);
    }



}
