package hansung.hansung_connect.domain.commnet.service;

import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.common.exception.code.status.ErrorStatus;
import hansung.hansung_connect.domain.commnet.converter.CommentConverter;
import hansung.hansung_connect.domain.commnet.dto.CommentResponseDto.CommentListResponse;
import hansung.hansung_connect.domain.commnet.entity.Comment;
import hansung.hansung_connect.domain.commnet.repository.CommentRepository;
import hansung.hansung_connect.domain.user.entity.User;
import hansung.hansung_connect.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentQueryServiceImpl implements CommentQueryService {

    private static final int PAGE_SIZE = 20;

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentConverter commentConverter;

    @Override
    public CommentListResponse getCommentsByUser(Long userId, int page) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Page<Comment> comments = commentRepository.findByUserId(
                userId,
                PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        return commentConverter.toCommentListResponse(comments);
    }
}
