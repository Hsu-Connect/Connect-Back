package hansung.hansung_connect.domain.post.repository;

import hansung.hansung_connect.domain.post.entity.Post;
import hansung.hansung_connect.domain.post.entity.enums.PostType;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByType(PostType postType, Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "WHERE p.type IN :types AND p.updatedAt >= :threshold " +
            "ORDER BY p.views DESC")
    Page<Post> findPopularPostsInLast24Hours(
            @Param("types") List<PostType> types,
            @Param("threshold") LocalDateTime threshold,
            Pageable pageable);
}
