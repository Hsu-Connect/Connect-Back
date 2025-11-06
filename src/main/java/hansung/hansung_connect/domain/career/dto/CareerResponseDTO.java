package hansung.hansung_connect.domain.career.dto;

import hansung.hansung_connect.domain.career.entity.enums.JobType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class CareerResponseDTO {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "CareerResponse", title = "커리어 단건 응답")
    public static class CreateResponseDTO {
        @Schema(description = "커리어 ID", example = "1")
        private Long id;

        @Schema(description = "회사명", example = "한성대학교")
        private String companyName;

        @Schema(description = "직무명", example = "영업직")
        private String position;

        @Schema(description = "재직 형태", example = "PERMANENT")
        private JobType jobType;

        @Schema(description = "재직 여부", example = "false")
        private boolean employed;

        @Schema(description = "근무 시작 연월", example = "2024-04")
        private String startYm;

        @Schema(description = "근무 종료 연월 (재직중이면 null)", example = "2024-08")
        private String endYm;
    }
}

