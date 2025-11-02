package hansung.hansung_connect.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "PostCreateResponse", title = "게시글 작성 응답")
    public static class PostCreateResponse {

        @Schema(description = "게시글 ID", example = "123")
        private Long postId;

    }
}
