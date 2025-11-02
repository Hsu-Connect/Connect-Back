package hansung.hansung_connect.domain.post.entity;

import hansung.hansung_connect.domain.post.entity.enums.PostType;
import hansung.hansung_connect.domain.user.entity.User;
import hansung.hansung_connect.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostType type;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 500, nullable = false)
    private String body;

    private Long views;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
