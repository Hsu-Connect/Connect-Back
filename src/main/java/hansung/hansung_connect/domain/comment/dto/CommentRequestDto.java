package hansung.hansung_connect.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentRequestDto {

    @Getter
    @NoArgsConstructor
    @Schema(name = "CommentCreateRequest", title = "댓글 작성 요청")
    public static class CommentCreateRequest {

        @Schema(description = "댓글 내용", example = "좋은 게시글입니다!")
        private String content;

    }
}
