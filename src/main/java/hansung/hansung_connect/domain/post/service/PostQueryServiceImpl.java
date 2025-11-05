package hansung.hansung_connect.domain.post.service;

import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.common.exception.code.status.ErrorStatus;
import hansung.hansung_connect.domain.commnet.entity.Comment;
import hansung.hansung_connect.domain.commnet.repository.CommentRepository;
import hansung.hansung_connect.domain.post.converter.PostConverter;
import hansung.hansung_connect.domain.post.dto.PostResponseDto;
import hansung.hansung_connect.domain.post.dto.PostResponseDto.PostResponse;
import hansung.hansung_connect.domain.post.entity.Post;
import hansung.hansung_connect.domain.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostConverter postConverter;

    @Override
    public PostResponse getPost(Long userId, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId);

        List<PostResponseDto.PostCommentResponse> commentResponses = comments.stream()
                .map(comment -> postConverter.toPostCommentResponse(comment, userId))
                .toList();

        return postConverter.toPostResponse(post, userId, commentResponses);
    }
}
