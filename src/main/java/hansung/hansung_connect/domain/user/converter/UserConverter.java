package hansung.hansung_connect.domain.user.converter;

import hansung.hansung_connect.domain.user.dto.UserResponseDTO;
import hansung.hansung_connect.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserResponseDTO.SummaryCardResponse toSummaryCardResponse(User user, boolean employed) {
        return UserResponseDTO.SummaryCardResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .studentNumberPrefix(extractStudentNumberPrefix(user.getStudentNumber()))
                .major(user.getMajor())
                .jobSeeking(user.isJobSeeking())
                .academicStatus(user.getAcademicStatus())
                .employed(employed)
                .build();
    }

    // 학번 앞 두 자리만 반환
    private String extractStudentNumberPrefix(String studentNumber) {
        if (studentNumber == null) {
            return "";
        }
        String trimmed = studentNumber.trim();
        if (trimmed.length() < 2) {
            return "";
        }
        return trimmed.substring(0, 2);
    }
}
