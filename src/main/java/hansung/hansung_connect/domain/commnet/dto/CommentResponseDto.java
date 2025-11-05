package hansung.hansung_connect.domain.commnet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "CommentCreateResponse", title = "댓글 작성 응답")
    public static class CommentCreateResponse {

        @Schema(description = "댓글 ID", example = "123")
        private Long commentId;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "CommentListResponse", title = "댓글 목록 조회 응답")
    public static class CommentListResponse {

        @Schema(description = "댓글 정보")
        private List<CommentResponse> comments;

        @Schema(description = "현재 페이지 번호 (0부터 시작)", example = "0")
        private int currentPage;

        @Schema(description = "다음 페이지 존재 여부", example = "true")
        private boolean hasNext;

        @Schema(description = "총 게시글 수", example = "132")
        private long totalElements;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "CommentListResponse", title = "댓글 조회 응답")
    public static class CommentResponse {

        @Schema(description = "댓글 ID", example = "123")
        private Long commentId;

        @Schema(description = "댓글 작성자", example = "장우진")
        private String commenter;

        @Schema(description = "작성자 학번", example = "20학번")
        private String studentId;

        @Schema(description = "댓글 내용", example = "안녕하세요, 댓글입니다.")
        private String content;

    }
}
