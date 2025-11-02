package hansung.hansung_connect.domain.post.dto;

import hansung.hansung_connect.domain.post.entity.enums.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostRequestDto {

    @Getter
    @NoArgsConstructor
    @Schema(name = "CreatePost", title = "게시글 작성 요청")
    public static class PostCreateRequest {

        @Schema(description = "게시판 유형 [FREE, PROMOTION]", example = "FREE, PROMOTION")
        private PostType postType;

        @Schema(description = "게시글 제목", example = "한성대 채용 공고")
        private String title;

        @Schema(description = "게시글 본문", example = "한성대에서 채용 공고가 올라왔습니다.")
        private String body;
    }
}
