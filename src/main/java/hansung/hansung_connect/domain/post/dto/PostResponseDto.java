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
    @Builder
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
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "PostSummaryListResponse", title = "게시글의 요약 정보 리스트 응답")
    public static class PostSummaryListResponse {
        @Schema(description = "게시글 ID", example = "123")
        private List<PostSummaryResponse> posts;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "PostListResponse", title = "게시글 목록 조회 응답")
    public static class PostListResponse {
        @Schema(description = "게시글 요약 정보")
        private List<PostSummaryResponse> posts;

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
    @Schema(name = "PostResponse", title = "게시글 상세 조회 응답")
    public static class PostResponse {
        @Schema(description = "게시글 ID", example = "123")
        private Long id;

        @Schema(description = "게시글 작성자", example = "장우진")
        private String author;

        @Schema(description = "작성자 학번", example = "20학번")
        private String studentId;

        @Schema(description = "작성자 상태", example = "재직중")
        private String authorStatus;

        @Schema(description = "게시글 제목", example = "저메추")
        private String title;

        @Schema(description = "게시글 본문", example = "저녁 메뉴 추천해주세요")
        private String body;

        @Schema(description = "자신의 게시글인지 여부", example = "false")
        private boolean mine;

        @Schema(description = "게시글의 댓글 리스트")
        private List<PostCommentResponse> comments;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "PostCommentResponse", title = "게시글의 댓글 리스트 응답")
    public static class PostCommentResponse {
        @Schema(description = "댓글 ID", example = "123")
        private Long id;

        @Schema(description = "댓글 작성자", example = "장우진")
        private String commenter;

        @Schema(description = "댓글 작성자 상태", example = "재직중")
        private String commenterStatus;

        @Schema(description = "댓글 내용", example = "치킨드세요")
        private String content;

        @Schema(description = "자신의 댓글인지 여부", example = "false")
        private boolean mine;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "PostTitleListResponse", title = "게시글의 제목 리스트 응답")
    public static class PostTitleListResponse {
        @Schema(description = "게시글 ID", example = "123")
        private List<PostTitleResponse> posts;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "PostTitleResponse", title = "게시글의 제목 응답")
    public static class PostTitleResponse {
        @Schema(description = "게시글 ID", example = "123")
        private Long id;

        @Schema(description = "게시글 제목", example = "실시간 심장병 질문 1위")
        private String title;

    }
}
