package hansung.hansung_connect.domain.commnet.service;

import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.common.exception.code.status.ErrorStatus;
import hansung.hansung_connect.domain.commnet.converter.CommentConverter;
import hansung.hansung_connect.domain.commnet.dto.CommentRequestDto.CommentCreateRequest;
import hansung.hansung_connect.domain.commnet.dto.CommentResponseDto.CommentCreateResponse;
import hansung.hansung_connect.domain.commnet.entity.Comment;
import hansung.hansung_connect.domain.commnet.repository.CommentRepository;
import hansung.hansung_connect.domain.post.entity.Post;
import hansung.hansung_connect.domain.post.repository.PostRepository;
import hansung.hansung_connect.domain.user.entity.User;
import hansung.hansung_connect.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentConverter commentConverter;

    @Override
    public CommentCreateResponse createComment(Long userId, Long postId, CommentCreateRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        Comment comment = commentConverter.toComment(user, post, request);
        commentRepository.save(comment);

        return commentConverter.toCommentCreateResponse(comment);
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));

        User commentAuthor = comment.getUser();
        User postAuthor = comment.getPost().getUser();

        if(!commentAuthor.getId().equals(userId) && !postAuthor.getId().equals(userId)) {
            throw new GeneralException(ErrorStatus.COMMENT_FORBIDDEN);
        }

        commentRepository.delete(comment);
    }
}
