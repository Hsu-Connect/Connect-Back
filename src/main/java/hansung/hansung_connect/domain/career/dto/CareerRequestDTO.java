package hansung.hansung_connect.domain.career.dto;

import hansung.hansung_connect.domain.career.entity.enums.JobType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 커리어 요청 DTO 모음 - API별 요청 스펙을 이너 클래스로 관리
 */
public class CareerRequestDTO {

    @Getter
    @NoArgsConstructor
    @Schema(name = "CareerCreateRequest", title = "커리어 등록 요청")
    public static class CreateRequestDTO {
        @Schema(description = "회사명", example = "한성대학교")
        private String companyName;

        @Schema(description = "직무명", example = "영업직")
        private String position;

        @Schema(description = "재직 형태", example = "PERMANENT")
        private JobType jobType;

        @Schema(description = "근무 시작 연월", example = "2024-04")
        private String startYm;

        @Schema(description = "근무 종료 연월 (재직중이면 null)", example = "2024-08")
        private String endYm;

        @Schema(description = "재직 여부", example = "true")
        private boolean employed;
    }

}


