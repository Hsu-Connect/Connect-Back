package hansung.hansung_connect.domain.user.dto;

import hansung.hansung_connect.domain.user.entity.enums.AcademicStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SummaryCardResponse {
        private Long userId;                 // 사용자 PK
        private String name;                 // 이름
        private String studentNumberPrefix;  // 학번 앞 두 자리 (예: "21")
        private String major;                // 전공
        private boolean jobSeeking;          // 구직 여부
        private AcademicStatus academicStatus; // 재학/졸업 등 상태
        private boolean employed;            // 재직 중 여부 (Career.isEmployed 존재 여부)
    }
}
