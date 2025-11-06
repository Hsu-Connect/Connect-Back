package hansung.hansung_connect.domain.user.repository;

import hansung.hansung_connect.domain.user.entity.User;
import hansung.hansung_connect.domain.user.entity.enums.UserStatus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakaoUserId(Long kakaoUserId);

    boolean existsByStudentNumber(String studentNumber);

    Optional<User> findById(Long id);

    long countByMentorTrueAndStatus(UserStatus status);

    @Query("""
            select u
            from User u
            where u.mentor = true
              and u.status = :status
            order by case when u.major = :major then 0 else 1 end, u.id desc
            """)
    Page<User> findMentorsOrderByMajorPreference(@Param("major") String major,
                                                 @Param("status") UserStatus status,
                                                 Pageable pageable);
}
