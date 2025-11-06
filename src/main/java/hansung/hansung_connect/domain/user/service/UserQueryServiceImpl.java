package hansung.hansung_connect.domain.user.service;

import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.common.exception.code.status.ErrorStatus;
import hansung.hansung_connect.domain.career.entity.Career;
import hansung.hansung_connect.domain.career.repository.CareerRepository;
import hansung.hansung_connect.domain.link.entity.Link;
import hansung.hansung_connect.domain.link.repository.LinkRepository;
import hansung.hansung_connect.domain.user.converter.UserConverter;
import hansung.hansung_connect.domain.user.dto.UserResponseDTO;
import hansung.hansung_connect.domain.user.entity.User;
import hansung.hansung_connect.domain.user.entity.enums.UserStatus;
import hansung.hansung_connect.domain.user.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final CareerRepository careerRepository;
    private final LinkRepository linkRepository;
    private final UserConverter userConverter;

    @Override
    public UserResponseDTO.SummaryCardResponse getMySummaryCard(Long currentUserId) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        // 재직 여부 존재 검사 (Career에 isEmployed == true 인 레코드)
        boolean employed = careerRepository.existsByUser_IdAndIsEmployedTrue(currentUserId);

        return userConverter.toSummaryCardResponse(user, employed);
    }

    @Override
    public UserResponseDTO.MyProfileResponse getMyProfile(Long currentUserId) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        List<Career> careers = careerRepository.findAllByUser_Id(currentUserId);
        List<Link> links = linkRepository.findAllByUser_Id(currentUserId);

        return userConverter.toMyProfileResponse(user, careers, links);
    }

    @Override
    public UserResponseDTO.MentorListResponse getMentors(Long currentUserId, int page, int size) {
        User me = userRepository.findById(currentUserId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        String myMajor = me.getMajor();

        long totalMentorCount = userRepository.countByMentorTrueAndStatus(UserStatus.ACTIVATE);

        Pageable pageable = PageRequest.of(page, size);
        Page<User> mentorPage = userRepository.findMentorsOrderByMajorPreference(
                myMajor, UserStatus.ACTIVATE, pageable);

        List<Long> userIds = mentorPage.map(User::getId).getContent();
        Set<Long> employedUserIds = new HashSet<>(careerRepository.findUserIdsEmployedTrueIn(userIds));
        
        return userConverter.toMentorListResponse(mentorPage, employedUserIds, totalMentorCount);
    }
}

