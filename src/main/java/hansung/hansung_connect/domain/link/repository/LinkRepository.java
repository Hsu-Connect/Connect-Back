package hansung.hansung_connect.domain.link.repository;

import hansung.hansung_connect.domain.link.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
}

