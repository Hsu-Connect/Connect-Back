package hansung.hansung_connect.domain.user.service;

import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.common.exception.code.status.ErrorStatus;
import hansung.hansung_connect.domain.career.repository.CareerRepository;
import hansung.hansung_connect.domain.user.converter.UserConverter;
import hansung.hansung_connect.domain.user.dto.UserResponseDTO;
import hansung.hansung_connect.domain.user.entity.User;
import hansung.hansung_connect.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final CareerRepository careerRepository;
    private final UserConverter userConverter;

    @Override
    public UserResponseDTO.SummaryCardResponse getMySummaryCard(Long currentUserId) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        // 재직 여부 존재 검사 (Career에 isEmployed == true 인 레코드)
        boolean employed = careerRepository.existsByUser_IdAndIsEmployedTrue(currentUserId);

        return userConverter.toSummaryCardResponse(user, employed);
    }
}

