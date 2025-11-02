package hansung.hansung_connect.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "PostSummaryResponse", title = "게시글 요약 정보")
    public static class PostSummaryResponse {
        @Schema(description = "게시글 ID", example = "12")
        private Long id;

        @Schema(description = "게시글 제목", example = "저메추")
        private String title;

        @Schema(description = "게시글 요약", example = "저녁 메뉴 추천해주세요...")
        private String summary;

        @Schema(description = "작성자", example = "장우진")
        private String author;

        @Schema(description = "작성자 학번", example = "20학번")
        private String studentId;

        @Schema(description = "작성자 상태", example = "구직중")
        private String authorStatus;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "PostListResponse", title = "게시글 목록 조회 응답")
    public static class PostListResponse {
        @Schema(description = "게시글 요약 정보")
        private List<PostSummaryResponse> posts;
    }
}
