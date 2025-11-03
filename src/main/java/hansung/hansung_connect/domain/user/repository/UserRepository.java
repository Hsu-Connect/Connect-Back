package hansung.hansung_connect.domain.user.repository;

import hansung.hansung_connect.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
