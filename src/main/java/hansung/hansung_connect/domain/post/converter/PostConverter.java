package hansung.hansung_connect.domain.post.converter;

import hansung.hansung_connect.domain.comment.entity.Comment;
import hansung.hansung_connect.domain.post.dto.PostRequestDto;
import hansung.hansung_connect.domain.post.dto.PostResponseDto;
import hansung.hansung_connect.domain.post.dto.PostResponseDto.PostCommentResponse;
import hansung.hansung_connect.domain.post.entity.Post;
import hansung.hansung_connect.domain.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    private static final int SUMMARY_LENGTH = 30;

    public Post toPost(User user, PostRequestDto.PostCreateRequest request) {
        return Post.builder()
                .type(request.getPostType())
                .title(request.getTitle())
                .body(request.getBody())
                .views(0L)
                .user(user)
                .build();
    }

    public PostResponseDto.PostCreateResponse toPostCreateResponse(Post post) {
        return PostResponseDto.PostCreateResponse.builder()
                .postId(post.getId())
                .build();
    }

    public PostResponseDto.PostUpdateResponse toPostUpdateResponse(Post post) {
        return PostResponseDto.PostUpdateResponse.builder()
                .postId(post.getId())
                .build();
    }

    public PostResponseDto.PostResponse toPostResponse(Post post, Long currentUserId,
                                                       List<PostCommentResponse> commentResponses) {
        return PostResponseDto.PostResponse.builder()
                .id(post.getId())
                .author(post.getUser().getName())
                .studentId(post.getUser().getStudentNumber())
                .authorStatus(post.getUser().getAcademicStatus().getStatus())
                .title(post.getTitle())
                .body(post.getBody())
                .mine(post.getUser().getId().equals(currentUserId))
                .comments(commentResponses)
                .build();
    }

    public PostResponseDto.PostCommentResponse toPostCommentResponse(Comment comment, Long currentUserId) {
        return PostResponseDto.PostCommentResponse.builder()
                .id(comment.getId())
                .commenter(comment.getUser().getName())
                .commenterStatus(comment.getUser().getAcademicStatus().toString())
                .content(comment.getBody())
                .mine(comment.getUser().getId().equals(currentUserId))
                .build();
    }

    public PostResponseDto.PostSummaryResponse toPostSummaryResponse(Post post) {
        String body = post.getBody();
        String summary = body.length() <= SUMMARY_LENGTH
                ? body
                : body.substring(0, SUMMARY_LENGTH) + "...";

        return PostResponseDto.PostSummaryResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .summary(summary)
                .author(post.getUser().getName())
                .studentId(post.getUser().getStudentNumber())
                .authorStatus(post.getUser().getAcademicStatus().getStatus())
                .build();
    }

    public PostResponseDto.PostSummaryListResponse toPostSummaryListResponse(Page<Post> postPage) {
        List<PostResponseDto.PostSummaryResponse> posts = postPage.getContent().stream()
                .map(this::toPostSummaryResponse)
                .toList();

        return PostResponseDto.PostSummaryListResponse.builder()
                .posts(posts)
                .build();
    }

    public PostResponseDto.PostListResponse toPostListResponse(Page<Post> postPage) {
        List<PostResponseDto.PostSummaryResponse> posts = postPage.getContent().stream()
                .map(this::toPostSummaryResponse)
                .toList();

        return PostResponseDto.PostListResponse.builder()
                .posts(posts)
                .currentPage(postPage.getNumber())
                .hasNext(postPage.hasNext())
                .totalElements(postPage.getTotalElements())
                .build();
    }

    public PostResponseDto.PostTitleListResponse toPostTitleListResponse(Page<Post> postPage) {
        List<PostResponseDto.PostTitleResponse> posts = postPage.getContent().stream()
                .map(this::toPostTitleResponse)
                .toList();

        return PostResponseDto.PostTitleListResponse.builder()
                .posts(posts)
                .build();
    }

    public PostResponseDto.PostTitleResponse toPostTitleResponse(Post post) {
        return PostResponseDto.PostTitleResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .build();
    }
}
