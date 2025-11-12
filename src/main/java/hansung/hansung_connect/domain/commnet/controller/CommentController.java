package hansung.hansung_connect.domain.commnet.controller;

import hansung.hansung_connect.auth.token.JwtAuthFilter;
import hansung.hansung_connect.common.response.ApiResponse;
import hansung.hansung_connect.domain.commnet.dto.CommentRequestDto;
import hansung.hansung_connect.domain.commnet.dto.CommentResponseDto;
import hansung.hansung_connect.domain.commnet.service.CommentCommandService;
import hansung.hansung_connect.domain.commnet.service.CommentQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Comment", description = "댓글 관련 API")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;

    @Operation(
            summary = "댓글 작성",
            description = "댓글을 작성하는 API입니다. "
    )
    @PostMapping("/posts/{postId}/comments")
    public ApiResponse<CommentResponseDto.CommentCreateResponse> createComment(
            @Parameter(hidden = true) @AuthenticationPrincipal JwtAuthFilter.SimpleUserPrincipal me,
            @Parameter(description = "게시글 아이디", example = "1")
            @PathVariable("postId") Long postId,
            @RequestBody CommentRequestDto.CommentCreateRequest request
    ) {
        return ApiResponse.onSuccess(commentCommandService.createComment(me.id(), postId, request));
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
            @Parameter(hidden = true) @AuthenticationPrincipal JwtAuthFilter.SimpleUserPrincipal me,
            @Parameter(description = "페이지 번호")
            @RequestParam(defaultValue = "0") int page
    ) {
        return ApiResponse.onSuccess(commentQueryService.getCommentsByUser(me.id(), page));
    }

    @Operation(
            summary = "댓글 삭제",
            description = """
                    댓글을 삭제하는 API입니다.
                    Path Variable로 댓글 아이디를 입력해주세요.
                    - 게시글의 작성자인 경우 모든 댓글 삭제 가능
                    - 게시글의 작성자가 아닌 경우 자신의 댓글만 삭제 가능
                    """
    )
    @DeleteMapping
    public ApiResponse<Void> deleteComment(
            @Parameter(hidden = true) @AuthenticationPrincipal JwtAuthFilter.SimpleUserPrincipal me,
            @PathVariable("commentId") Long commentId
    ) {
        commentCommandService.deleteComment(me.id(), commentId);
        return ApiResponse.onSuccess(null);
    }

}
