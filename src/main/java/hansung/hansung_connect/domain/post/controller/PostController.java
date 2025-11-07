package hansung.hansung_connect.domain.post.controller;

import hansung.hansung_connect.common.response.ApiResponse;
import hansung.hansung_connect.domain.post.dto.PostRequestDto;
import hansung.hansung_connect.domain.post.dto.PostResponseDto;
import hansung.hansung_connect.domain.post.dto.enums.PostQueryType;
import hansung.hansung_connect.domain.post.service.PostCommandService;
import hansung.hansung_connect.domain.post.service.PostQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;

    @Operation(
            summary = "게시글 작성",
            description = "게시글을 작성하는 API입니다. postType으로 게시글 유형을 입력해주세요."
    )
    @PostMapping("")
    public ApiResponse<PostResponseDto.PostCreateResponse> createPost(
            @RequestBody PostRequestDto.PostCreateRequest request
    ) {
        Long userId = 1L;
        return ApiResponse.onSuccess(postCommandService.createPost(userId, request));
    }

    @Operation(
            summary = "게시글 리스트 조회",
            description = """
            게시글 유형별 리스트를 조회합니다.
            - popular: 인기글
            - free: 자유 게시판
            - promotion: 홍보 게시판
            - notice: 공지 게시글
            
            한 페이지에 게시글의 수는 20입니다.
            """
    )
    @GetMapping("")
    public ApiResponse<PostResponseDto.PostListResponse> getPosts(
            @Parameter(description = "게시글 조회 유형", example = "popular")
            @RequestParam(defaultValue = "popular") String type,
            @Parameter(description = "페이지 번호")
            @RequestParam(defaultValue = "0") int page
    ) {
        PostQueryType postType = PostQueryType.from(type);
        return ApiResponse.onSuccess(postQueryService.getPostsByType(postType, page));
    }

    @Operation(
            summary = "게시글 단건 조회",
            description = "하나의 게시글을 조회하는 API입니다. Path Variable로 게시글 아이디를 입력해주세요."
    )
    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDto.PostResponse> getPost(
            @PathVariable("postId") Long postId
    ) {
        Long userId = 1L;
        return ApiResponse.onSuccess(postQueryService.getPost(userId, postId));
    }

    @Operation(
            summary = "내 게시글 리스트 조회",
            description = """
            작성한 게시글의 리스트를 조회하는 API입니다.
            
            한 페이지에 게시글의 수는 20입니다.
            """
    )
    @GetMapping("/my")
    public ApiResponse<PostResponseDto.PostListResponse> getMyPosts(
            @Parameter(description = "페이지 번호")
            @RequestParam(defaultValue = "0") int page
    ) {
        Long userId = 1L;
        return ApiResponse.onSuccess(postQueryService.getPostsByUser(userId, page));
    }

    @GetMapping("/popular")
    @Operation(
            summary = "메인화면 - 인기 게시글 조회",
            description = """
                    메인화면에서 인기 게시글 5개의 제목을 제공합니다.
                    인기 게시글: 지난 24시간 동안 조회 수가 가장 높은 게시글
                    """
    )
    public ApiResponse<PostResponseDto.PostTitleListResponse> getPopularPosts() {
        return ApiResponse.onSuccess(postQueryService.getPopularPosts());
    }

    @GetMapping("/promotion")
    @Operation(
            summary = "메인화면 - 최신 홍보 게시글 조회",
            description = """
                    메인화면에서 최신 홍보 게시글 5개의 요약 응답을 제공합니다.
                    """
    )
    public ApiResponse<PostResponseDto.PostSummaryListResponse> getLatestPromotionPosts() {
        return ApiResponse.onSuccess(postQueryService.getLatestPromotionPosts());
    }

    @PatchMapping("/{postId}")
    @Operation(
            summary = "게시글 수정",
            description = """
                    게시글을 수정하는 API입니다. 제목, 내용 중 수정된 부분만 전달
                    Path Variable로 게시글 아이디를 입력해주세요.
                    -title: 게시글 제목, null 허용
                    -body: 게시글 내용 , null 허용
                    """
    )
    public ApiResponse<PostResponseDto.PostUpdateResponse> updatePost(
            @PathVariable("postId") Long postId,
            @RequestBody PostRequestDto.PostUpdateRequest request
    ) {

        Long userId = 1L;

        return ApiResponse.onSuccess(postCommandService.updatePost(userId, postId, request));
    }

    @DeleteMapping("/{postId}")
    @Operation(
            summary = "게시글 삭제",
            description = """
                    게시글을 삭제 API입니다.
                    Path Variable로 게시글 아이디를 입력해주세요.
                    """
    )
    public ApiResponse<Void> deletePost(
            @PathVariable("postId") Long postId
    ) {

        Long userId = 1L;
        postCommandService.deletePost(userId, postId);
        return ApiResponse.onSuccess(null);
    }

}
