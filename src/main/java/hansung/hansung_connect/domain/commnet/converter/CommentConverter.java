package hansung.hansung_connect.domain.commnet.converter;

import hansung.hansung_connect.domain.commnet.dto.CommentRequestDto;
import hansung.hansung_connect.domain.commnet.dto.CommentResponseDto;
import hansung.hansung_connect.domain.commnet.entity.Comment;
import hansung.hansung_connect.domain.post.entity.Post;
import hansung.hansung_connect.domain.user.entity.User;
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
}
