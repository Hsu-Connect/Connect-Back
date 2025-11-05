package hansung.hansung_connect.domain.post.service;

import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.common.exception.code.status.ErrorStatus;
import hansung.hansung_connect.domain.commnet.entity.Comment;
import hansung.hansung_connect.domain.commnet.repository.CommentRepository;
import hansung.hansung_connect.domain.post.converter.PostConverter;
import hansung.hansung_connect.domain.post.dto.PostResponseDto;
import hansung.hansung_connect.domain.post.dto.PostResponseDto.PostListResponse;
import hansung.hansung_connect.domain.post.dto.PostResponseDto.PostResponse;
import hansung.hansung_connect.domain.post.dto.enums.PostQueryType;
import hansung.hansung_connect.domain.post.entity.Post;
import hansung.hansung_connect.domain.post.entity.enums.PostType;
import hansung.hansung_connect.domain.post.repository.PostRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

    private static final int PAGE_SIZE = 20;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostConverter postConverter;

    @Override
    @Transactional
    public PostResponse getPost(Long userId, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        post.increaseViews();

        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId);

        List<PostResponseDto.PostCommentResponse> commentResponses = comments.stream()
                .map(comment -> postConverter.toPostCommentResponse(comment, userId))
                .toList();

        return postConverter.toPostResponse(post, userId, commentResponses);
    }

    @Override
    public PostListResponse getPostsByType(PostQueryType type, int page) {
        Page<Post> posts;

        switch (type) {
            case PostQueryType.POPULAR:
                posts = getPopularPosts(page);
                break;
            case PostQueryType.FREE:
                posts = getFreePosts(page);
                break;
            case PostQueryType.PROMOTION:
                posts = getPromotionPosts(page);
                break;
            case PostQueryType.NOTICE:
                posts = getNoticePosts(page);
                break;
            default:
                throw new GeneralException(ErrorStatus.POST_QUERY_TYPE_EXCEPTION);
        }

        if(posts == null) {
            throw new GeneralException(ErrorStatus.POST_LIST_NOT_FOUND);
        }

        return postConverter.toPostListResponse(posts);
    }

    private Page<Post> getPopularPosts(int page) {

        if(page != 0) {
            throw new GeneralException(ErrorStatus.INVALID_PAGE_FOR_POPULAR);
        }

        LocalDateTime threshold = LocalDateTime.now().minusHours(24);

        Page<Post> posts = postRepository.findPopularPostsInLast24Hours(
                List.of(PostType.FREE, PostType.PROMOTION),
                threshold,
                PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "views"))
        );

        return posts;
    }

    private Page<Post> getFreePosts(int page) {

        Page<Post> posts = postRepository.findByType(
                PostType.FREE,
                PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        return posts;
    }

    private Page<Post> getPromotionPosts(int page) {

        Page<Post> posts = postRepository.findByType(
                PostType.PROMOTION,
                PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        return posts;
    }

    private Page<Post> getNoticePosts(int page) {

        Page<Post> posts = postRepository.findByType(
                PostType.NOTICE,
                PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        return posts;
    }
}
