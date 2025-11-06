package hansung.hansung_connect.domain.user.service;

import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.common.exception.code.status.ErrorStatus;
import hansung.hansung_connect.domain.user.dto.UserRequestDTO;
import hansung.hansung_connect.domain.user.entity.User;
import hansung.hansung_connect.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;

    @Override
    public void updateMyBasicProfile(Long currentUserId,
                                     UserRequestDTO.UpdateBasicProfileRequest request) {

        // 1) 본인 계정 조회
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        // 2) 부분수정 적용 (null 미반영)
        user.updateBasicProfile(
                request.getStudentNumber(),
                request.getName(),
                request.getMajor(),
                request.getMentor(),
                request.getJobSeeking()
        );

        // 3) JPA dirty checking으로 update 반영 (명시 save 불필요)
        // 필요 시 명시 저장:
        // userRepository.save(user);
    }
}
