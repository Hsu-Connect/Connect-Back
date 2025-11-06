package hansung.hansung_connect.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UpdateBasicProfileRequest {

        @Schema(description = "학번", example = "2131114")
        @Size(max = 10)
        private String studentNumber;

        @Schema(description = "이름", example = "아라치")
        @Size(max = 50)
        private String name;

        @Schema(description = "전공", example = "회계재무경영")
        @Size(max = 50)
        private String major;

        @Schema(description = "멘토 참여 여부", example = "true")
        private Boolean mentor;

        @Schema(description = "구직 여부", example = "true")
        private Boolean jobSeeking;
    }
}
