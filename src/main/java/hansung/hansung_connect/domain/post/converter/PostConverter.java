package hansung.hansung_connect.domain.post.converter;

import hansung.hansung_connect.domain.commnet.entity.Comment;
import hansung.hansung_connect.domain.post.dto.PostRequestDto;
import hansung.hansung_connect.domain.post.dto.PostResponseDto;
import hansung.hansung_connect.domain.post.dto.PostResponseDto.PostCommentResponse;
import hansung.hansung_connect.domain.post.entity.Post;
import hansung.hansung_connect.domain.user.entity.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

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

    public PostResponseDto.PostResponse toPostResponse(Post post, List<Comment> comments) {
        return PostResponseDto.PostResponse.builder()
                .id(post.getId())
                .author(post.getUser().getName())

                .build();

    }

    public PostResponseDto.PostResponse toPostResponse(Post post, Long currentUserId, List<PostCommentResponse> commentResponses) {
        return PostResponseDto.PostResponse.builder()
                .id(post.getId())
                .author(post.getUser().getName())
                .studentId(post.getUser().getStudentNumber())
                .authorStatus(post.getUser().getAcademicStatus().toString())
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
}
