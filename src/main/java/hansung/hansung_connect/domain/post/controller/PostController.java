package hansung.hansung_connect.domain.post.controller;

import hansung.hansung_connect.common.response.ApiResponse;
import hansung.hansung_connect.domain.post.dto.PostRequestDto;
import hansung.hansung_connect.domain.post.dto.PostResponseDto;
import hansung.hansung_connect.domain.post.service.PostCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
            description = "게시글 리스트를 조회하는 API입니다. Query Parameter로 게시글 유형을 입력해주세요."
                    + " [popular, free, promotion, notice]"
    )
    @GetMapping("")
    public ApiResponse<PostResponseDto.PostListResponse> getPosts(
            @RequestParam(required = false, defaultValue = "popular") String type
    ) {
        return ApiResponse.onSuccess(null);
    }

    @Operation(
            summary = "게시글 단건 조회",
            description = "하나의 게시글을 조회하는 API입니다. Path Variable로 게시글 아이디를 입력해주세요."
    )
    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDto.PostResponse> getPost(
            @PathVariable("postId") Long postId
    ) {
        return ApiResponse.onSuccess(null);
    }

    @Operation(
            summary = "내 게시글 리스트 조회",
            description = "자신이 작성한 게시글의 리스트를 조회하는 API입니다."
    )
    @GetMapping("/my")
    public ApiResponse<PostResponseDto.PostListResponse> getMyPosts(

    ) {
        return ApiResponse.onSuccess(null);
    }

}
