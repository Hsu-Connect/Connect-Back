package hansung.hansung_connect.domain.commnet.repository;

import hansung.hansung_connect.domain.commnet.entity.Comment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostIdOrderByCreatedAtAsc(Long postId);

    Page<Comment> findByUserId(Long userId, Pageable pageable);
}
