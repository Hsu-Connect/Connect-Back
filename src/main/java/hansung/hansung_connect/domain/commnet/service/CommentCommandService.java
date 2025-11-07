package hansung.hansung_connect.domain.commnet.service;

import hansung.hansung_connect.domain.commnet.dto.CommentRequestDto;
import hansung.hansung_connect.domain.commnet.dto.CommentResponseDto;

public interface CommentCommandService {

    CommentResponseDto.CommentCreateResponse createComment(Long userId, Long postId, CommentRequestDto.CommentCreateRequest request);
    void deleteComment(Long userId, Long commentId);
}
