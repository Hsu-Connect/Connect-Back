package hansung.hansung_connect.domain.comment.converter;

import hansung.hansung_connect.domain.comment.dto.CommentRequestDto;
import hansung.hansung_connect.domain.comment.dto.CommentResponseDto;
import hansung.hansung_connect.domain.comment.entity.Comment;
import hansung.hansung_connect.domain.post.entity.Post;
import hansung.hansung_connect.domain.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    public Comment toComment(User user, Post post, CommentRequestDto.CommentCreateRequest request) {
        return Comment.builder()
                .body(request.getContent())
                .user(user)
                .post(post)
                .build();
    }

    public CommentResponseDto.CommentCreateResponse toCommentCreateResponse(Comment comment) {
        return CommentResponseDto.CommentCreateResponse.builder()
                .commentId(comment.getId())
                .build();
    }

    public CommentResponseDto.CommentResponse toCommentResponse(Comment comment) {
        return CommentResponseDto.CommentResponse.builder()
                .commentId(comment.getId())
                .commenter(comment.getUser().getName())
                .studentId(comment.getUser().getStudentNumber())
                .content(comment.getBody())
                .build();
    }

    public CommentResponseDto.CommentListResponse toCommentListResponse(Page<Comment> commentPage) {
        List<CommentResponseDto.CommentResponse> comments = commentPage.getContent().stream()
                .map(this::toCommentResponse)
                .toList();

        return CommentResponseDto.CommentListResponse.builder()
                .comments(comments)
                .currentPage(commentPage.getNumber())
                .hasNext(commentPage.hasNext())
                .totalElements(commentPage.getTotalElements())
                .build();
    }
}
