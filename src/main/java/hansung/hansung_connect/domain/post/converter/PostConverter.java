package hansung.hansung_connect.domain.post.converter;

import hansung.hansung_connect.domain.post.dto.PostRequestDto;
import hansung.hansung_connect.domain.post.dto.PostResponseDto;
import hansung.hansung_connect.domain.post.entity.Post;
import hansung.hansung_connect.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public Post toPost(User user, PostRequestDto.PostCreateRequest request) {
        return Post.builder()
                .type(request.getPostType())
                .title(request.getTitle())
                .body(request.getBody())
                .views(0L)
                .user(user)
                .build();
    }

    public PostResponseDto.PostCreateResponse toPostCreateResponse(Post post) {
        return PostResponseDto.PostCreateResponse.builder()
                .postId(post.getId())
                .build();
    }
}
