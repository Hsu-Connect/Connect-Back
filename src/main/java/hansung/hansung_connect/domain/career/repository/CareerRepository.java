package hansung.hansung_connect.domain.career.repository;

import hansung.hansung_connect.domain.career.entity.Career;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CareerRepository extends JpaRepository<Career, Long> {
    boolean existsByUser_IdAndIsEmployedTrue(Long userId);

    List<Career> findAllByUser_Id(Long userId);

    @Query("""
            select distinct c.user.id
            from Career c
            where c.user.id in :userIds
              and c.isEmployed = true
            """)
    List<Long> findUserIdsEmployedTrueIn(@Param("userIds") Collection<Long> userIds);
}

