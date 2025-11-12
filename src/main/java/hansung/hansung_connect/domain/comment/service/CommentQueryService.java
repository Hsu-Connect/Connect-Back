package hansung.hansung_connect.domain.comment.service;

import hansung.hansung_connect.domain.comment.dto.CommentResponseDto;

public interface CommentQueryService {

    CommentResponseDto.CommentListResponse getCommentsByUser(Long userId, int page);
}
