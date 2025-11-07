package hansung.hansung_connect.domain.post.service;

import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.common.exception.code.status.ErrorStatus;
import hansung.hansung_connect.domain.post.converter.PostConverter;
import hansung.hansung_connect.domain.post.dto.PostRequestDto.PostCreateRequest;
import hansung.hansung_connect.domain.post.dto.PostRequestDto.PostUpdateRequest;
import hansung.hansung_connect.domain.post.dto.PostResponseDto.PostCreateResponse;
import hansung.hansung_connect.domain.post.dto.PostResponseDto.PostUpdateResponse;
import hansung.hansung_connect.domain.post.entity.Post;
import hansung.hansung_connect.domain.post.repository.PostRepository;
import hansung.hansung_connect.domain.user.entity.User;
import hansung.hansung_connect.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {

    private final PostConverter postConverter;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public PostCreateResponse createPost(Long userId, PostCreateRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Post post = postRepository.save(postConverter.toPost(user, request));

        return postConverter.toPostCreateResponse(post);
    }

    @Override
    public PostUpdateResponse updatePost(Long userId, Long postId, PostUpdateRequest request) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        if(!post.getUser().getId().equals(userId)) {
            throw new GeneralException(ErrorStatus.POST_FORBIDDEN);
        }

        String title = request.getTitle();
        if(title != null) {
            post.updateTitle(title);
        }

        String body = request.getTitle();
        if(body != null) {
            post.updateBody(body);
        }

        postRepository.save(post);

        return postConverter.toPostUpdateResponse(post);
    }

}
