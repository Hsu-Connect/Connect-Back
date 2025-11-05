package hansung.hansung_connect.domain.commnet.service;

import hansung.hansung_connect.domain.commnet.dto.CommentResponseDto;

public interface CommentQueryService {

    CommentResponseDto.CommentListResponse getCommentsByUser(Long userId, int page);
}
