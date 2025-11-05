package hansung.hansung_connect.auth.dto;

import hansung.hansung_connect.domain.user.entity.enums.AcademicStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnboardingRequest {
    private String name;
    private String major;
    private String studentNumber;
    private AcademicStatus academicStatus;
    private boolean jobSeeking;
    private boolean mentor;
}
