package hansung.hansung_connect.domain.post.service;

import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.common.exception.code.status.ErrorStatus;
import hansung.hansung_connect.domain.post.converter.PostConverter;
import hansung.hansung_connect.domain.post.dto.PostRequestDto.PostCreateRequest;
import hansung.hansung_connect.domain.post.dto.PostResponseDto.PostCreateResponse;
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

}
