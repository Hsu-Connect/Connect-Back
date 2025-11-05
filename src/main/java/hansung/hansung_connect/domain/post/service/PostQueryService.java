package hansung.hansung_connect.domain.post.service;

import hansung.hansung_connect.domain.post.dto.PostResponseDto;

public interface PostQueryService {

    PostResponseDto.PostResponse getPost(Long userId, Long postId);
}
