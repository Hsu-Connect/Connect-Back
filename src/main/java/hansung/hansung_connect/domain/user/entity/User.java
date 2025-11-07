package hansung.hansung_connect.domain.user.entity;

import hansung.hansung_connect.domain.user.entity.enums.AcademicStatus;
import hansung.hansung_connect.domain.user.entity.enums.UserStatus;
import hansung.hansung_connect.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "ux_users_kakao_user_id", columnList = "kakao_user_id", unique = true),
                @Index(name = "ix_users_status", columnList = "status")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kakao
    @Column(name = "kakao_user_id", nullable = false, unique = true)
    private Long kakaoUserId;

    // 온보딩 flag
    @Column(name = "onboarded", nullable = false)
    private boolean onboarded = false;

    // 서비스 도메인 (온보딩으로 채움)
    @Column(name = "student_number", length = 10, unique = true)
    private String studentNumber;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "major", length = 50)
    private String major;

    @Enumerated(EnumType.STRING)
    @Column(name = "academic_status", length = 30, nullable = true)
    private AcademicStatus academicStatus;

    @Column(name = "job_seeking")
    private boolean jobSeeking;

    @Column(name = "mentor")
    private boolean mentor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private UserStatus status = UserStatus.ACTIVATE;

    @Column(name = "deactivated_at", nullable = true)
    private LocalDateTime deactivatedAt;

    @Column(name = "email")
    private String email;

    // 최초 카카오 로그인 시 사용
    public static User fromKakao(Long kakaoUserId) {
        User u = new User();
        u.kakaoUserId = kakaoUserId;
        u.onboarded = false;
        u.status = UserStatus.ACTIVATE;
        return u;
    }

    public void updateKakaoProfile(Long kakaoUserId) {
        this.kakaoUserId = kakaoUserId; // 안전상 재할당
    }

    public void completeOnboarding(String name,
                                   String major,
                                   String studentNumber,
                                   AcademicStatus status,
                                   boolean jobSeeking,
                                   boolean mentor, String email) {
        this.name = name;
        this.major = major;
        this.studentNumber = studentNumber;
        this.academicStatus = status;
        this.jobSeeking = jobSeeking;
        this.mentor = mentor;
        this.onboarded = true;
        this.email = email;
    }

    // 탈퇴,비활성화
    public void deactivate() {
        this.status = UserStatus.DEACTIVATE;
        this.deactivatedAt = LocalDateTime.now();
    }

    public void updateBasicProfile(String studentNumber,
                                   String name,
                                   String major,
                                   Boolean mentor,
                                   Boolean jobSeeking) {
        if (studentNumber != null) {
            this.studentNumber = studentNumber;
        }
        if (name != null) {
            this.name = name;
        }
        if (major != null) {
            this.major = major;
        }
        if (mentor != null) {
            this.mentor = mentor;
        }
        if (jobSeeking != null) {
            this.jobSeeking = jobSeeking;
        }
    }

}