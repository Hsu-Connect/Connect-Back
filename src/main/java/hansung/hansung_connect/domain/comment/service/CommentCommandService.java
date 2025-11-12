package hansung.hansung_connect.domain.comment.service;

import hansung.hansung_connect.domain.comment.dto.CommentRequestDto;
import hansung.hansung_connect.domain.comment.dto.CommentResponseDto;

public interface CommentCommandService {

    CommentResponseDto.CommentCreateResponse createComment(Long userId, Long postId,
                                                           CommentRequestDto.CommentCreateRequest request);

    void deleteComment(Long userId, Long commentId);
}
