package hansung.hansung_connect.domain.career.repository;

import hansung.hansung_connect.domain.career.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Long> {
}

