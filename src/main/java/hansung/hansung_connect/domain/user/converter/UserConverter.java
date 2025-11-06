package hansung.hansung_connect.domain.user.converter;

import hansung.hansung_connect.domain.career.entity.Career;
import hansung.hansung_connect.domain.link.entity.Link;
import hansung.hansung_connect.domain.user.dto.UserRequestDTO;
import hansung.hansung_connect.domain.user.dto.UserResponseDTO;
import hansung.hansung_connect.domain.user.entity.User;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    private static final DateTimeFormatter YM_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM");

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

    public UserResponseDTO.MyProfileResponse toMyProfileResponse(
            User user, List<Career> careers, List<Link> links) {

        return UserResponseDTO.MyProfileResponse.builder()
                .userId(user.getId())
                .studentNumber(user.getStudentNumber())
                .name(user.getName())
                .major(user.getMajor())
                .mentor(user.isMentor())
                .jobSeeking(user.isJobSeeking())
                .academicStatus(user.getAcademicStatus())
                .careers(careers.stream().map(this::toCareerItem).collect(Collectors.toList()))
                .links(links.stream().map(this::toLinkItem).collect(Collectors.toList()))
                .build();
    }

    private UserResponseDTO.CareerItem toCareerItem(Career c) {
        return UserResponseDTO.CareerItem.builder()
                .id(c.getId())
                .companyName(c.getCompanyName())
                .position(c.getPosition())
                .jobType(c.getJobType())
                .employed(c.isEmployed())
                .startYm(formatYm(c.getStartYm()))
                .endYm(formatYm(c.getEndYm()))
                .build();
    }

    private UserResponseDTO.LinkItem toLinkItem(Link l) {
        return UserResponseDTO.LinkItem.builder()
                .id(l.getId())
                .type(l.getType())
                .url(l.getUrl())
                .build();
    }

    private String formatYm(YearMonth ym) {
        return ym == null ? null : ym.format(YM_FORMAT);
    }

    public UserRequestDTO.UpdateBasicProfileRequest normalize(
            UserRequestDTO.UpdateBasicProfileRequest req) {

        if (req == null) {
            return null;
        }

        return UserRequestDTO.UpdateBasicProfileRequest.builder()
                .studentNumber(trimOrNull(req.getStudentNumber()))
                .name(trimOrNull(req.getName()))
                .major(trimOrNull(req.getMajor()))
                .mentor(req.getMentor())
                .jobSeeking(req.getJobSeeking())
                .build();
    }

    private String trimOrNull(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    public UserResponseDTO.MentorListResponse toMentorListResponse(
            Page<User> page, Set<Long> employedUserIds, long totalMentorCount) {

        List<UserResponseDTO.MentorCard> cards = page.getContent().stream()
                .map(u -> UserResponseDTO.MentorCard.builder()
                        .userId(u.getId())
                        .name(u.getName())
                        .major(u.getMajor())
                        .jobSeeking(u.isJobSeeking())
                        .employed(employedUserIds.contains(u.getId()))
                        .academicStatus(u.getAcademicStatus())
                        .build())
                .collect(Collectors.toList());

        return UserResponseDTO.MentorListResponse.builder()
                .totalMentorCount(totalMentorCount)
                .page(page.getNumber())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .items(cards)
                .build();
    }

    public UserResponseDTO.UserProfileResponse toUserProfileResponse(
            User user, boolean employed, List<Career> careers, List<Link> links) {

        return UserResponseDTO.UserProfileResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .studentNumberPrefix(extractStudentNumberPrefix(user.getStudentNumber()))
                .major(user.getMajor())
                .jobSeeking(user.isJobSeeking())
                .employed(employed)
                .academicStatus(user.getAcademicStatus())
                .email(user.getEmail())
                .careers(careers.stream()
                        .map(c -> UserResponseDTO.CareerItem.builder()
                                .id(c.getId())
                                .companyName(c.getCompanyName())
                                .position(c.getPosition())
                                .jobType(c.getJobType())
                                .employed(c.isEmployed())
                                .startYm(formatYm(c.getStartYm()))
                                .endYm(formatYm(c.getEndYm()))
                                .build())
                        .toList())
                .links(links.stream()
                        .map(l -> UserResponseDTO.LinkItem.builder()
                                .id(l.getId())
                                .type(l.getType())
                                .url(l.getUrl())
                                .build())
                        .toList())
                .build();
    }

}
