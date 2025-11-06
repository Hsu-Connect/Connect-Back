package hansung.hansung_connect.domain.link.dto;

import hansung.hansung_connect.domain.link.entity.enums.LinkType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

public class LinkRequestDTO {

    @Getter
    @NoArgsConstructor
    public static class CreateLinkDTO {

        @Schema(
                description = "링크 종류 (LINKEDIN, INSTAGRAM, GITHUB, NOTION, GOOGLE_DRIVE 중 하나)",
                example = "GITHUB",
                required = true
        )
        @NotNull(message = "링크 타입은 필수입니다.")
        private LinkType type;

        @Schema(
                description = "유효한 URL 주소",
                example = "https://github.com/username",
                required = true
        )
        @NotBlank(message = "URL은 비어 있을 수 없습니다.")
        @Size(max = 2048, message = "URL 길이가 너무 깁니다.")
        @URL(message = "유효한 URL 형식이 아닙니다.")
        private String url;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateLinkDTO {
        @Schema(description = "링크 종류", example = "NOTION", required = true)
        @NotNull(message = "링크 타입은 필수입니다.")
        private LinkType type;

        @Schema(description = "유효한 URL 주소", example = "https://www.notion.so/username", required = true)
        @NotBlank(message = "URL은 비어 있을 수 없습니다.")
        @Size(max = 2048, message = "URL 길이가 너무 깁니다.")
        @URL(message = "유효한 URL 형식이 아닙니다.")
        private String url;
    }
}
