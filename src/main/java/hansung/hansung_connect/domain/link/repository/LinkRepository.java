package hansung.hansung_connect.domain.link.repository;

import hansung.hansung_connect.domain.link.entity.Link;
import hansung.hansung_connect.domain.link.entity.enums.LinkType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
    boolean existsByUser_IdAndTypeAndIdNot(Long userId, LinkType type, Long id);

    List<Link> findAllByUser_Id(Long userId);
}

