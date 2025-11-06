package hansung.hansung_connect.domain.user.dto;

import hansung.hansung_connect.domain.career.entity.enums.JobType;
import hansung.hansung_connect.domain.link.entity.enums.LinkType;
import hansung.hansung_connect.domain.user.entity.enums.AcademicStatus;
import java.util.List;
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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MyProfileResponse {
        private Long userId;
        private String studentNumber;            // 학번
        private String name;                     // 이름
        private String major;                    // 전공
        private boolean mentor;                  // 멘토 참여 여부
        private boolean jobSeeking;              // 구직 여부
        private AcademicStatus academicStatus;   // 재학/졸업 등
        private List<CareerItem> careers;        // 내 커리어 전체
        private List<LinkItem> links;            // 자기소개 외부 링크 전체
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CareerItem {
        private Long id;
        private String companyName;
        private String position;
        private JobType jobType;
        private boolean employed;   // isEmployed
        private String startYm;     // "yyyy.MM"
        private String endYm;       // "yyyy.MM" or null
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LinkItem {
        private Long id;
        private LinkType type;
        private String url;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MentorCard {
        private Long userId;
        private String name;
        private String major;
        private boolean jobSeeking;
        private boolean employed;
        private AcademicStatus academicStatus; // 재학/졸업 여부 표현
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MentorListResponse {
        private long totalMentorCount;   // 전체 멘토 수(필터/전공 무관, mentor=true & 활성)
        private int page;                // 0-based
        private int size;                // 요청한 페이지 크기(기본 15)
        private int totalPages;
        private List<MentorCard> items;  // 카드 15개
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserProfileResponse {
        private Long userId;
        private String name;
        private String studentNumberPrefix;   // 학번 앞 두 자리 (예: "21")
        private String major;
        private String email;
        private boolean jobSeeking;
        private boolean employed;             // Career 기준
        private AcademicStatus academicStatus;
        private List<CareerItem> careers;     // 기존 CareerItem 재활용
        private List<LinkItem> links;         // 기존 LinkItem 재활용
    }

}
