package hansung.hansung_connect.domain.post.repository;

import hansung.hansung_connect.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
