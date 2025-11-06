package hansung.hansung_connect.domain.link.dto;

import hansung.hansung_connect.domain.link.entity.enums.LinkType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LinkResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LinkResultDTO {

        @Schema(description = "링크 고유 ID", example = "1")
        private Long id;

        @Schema(description = "링크 종류", example = "GITHUB")
        private LinkType type;

        @Schema(description = "등록된 링크 주소", example = "https://github.com/username")
        private String url;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LinkResultListDTO {

        @Schema(description = "생성된 링크 결과 목록")
        private List<LinkResultDTO> links;
    }

}
