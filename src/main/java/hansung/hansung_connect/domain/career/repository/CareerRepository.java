package hansung.hansung_connect.domain.career.repository;

import hansung.hansung_connect.domain.career.entity.Career;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Long> {
    boolean existsByUser_IdAndIsEmployedTrue(Long userId);

    List<Career> findAllByUser_Id(Long userId);

}

