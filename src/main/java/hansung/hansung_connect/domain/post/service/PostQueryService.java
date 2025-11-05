package hansung.hansung_connect.domain.post.service;

import hansung.hansung_connect.domain.post.dto.PostResponseDto;
import hansung.hansung_connect.domain.post.dto.enums.PostQueryType;

public interface PostQueryService {

    PostResponseDto.PostResponse getPost(Long userId, Long postId);
    PostResponseDto.PostListResponse getPostsByType(PostQueryType type, int page);
    PostResponseDto.PostListResponse getPostsByUser(Long userId, int page);
}
