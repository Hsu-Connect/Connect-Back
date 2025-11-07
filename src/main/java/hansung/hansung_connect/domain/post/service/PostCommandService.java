package hansung.hansung_connect.domain.post.service;

import hansung.hansung_connect.domain.post.dto.PostRequestDto;
import hansung.hansung_connect.domain.post.dto.PostResponseDto;

public interface PostCommandService {
    PostResponseDto.PostCreateResponse createPost(Long userId, PostRequestDto.PostCreateRequest request);
    PostResponseDto.PostUpdateResponse updatePost(Long userId, Long postId, PostRequestDto.PostUpdateRequest request);
    void deletePost(Long userId, Long postId);
}
