package hansung.hansung_connect.domain.user.service;

import hansung.hansung_connect.domain.user.dto.UserResponseDTO;

public interface UserQueryService {
    UserResponseDTO.SummaryCardResponse getMySummaryCard(Long currentUserId);

    UserResponseDTO.MyProfileResponse getMyProfile(Long currentUserId);

    UserResponseDTO.MentorListResponse getMentors(Long currentUserId, int page, int size);

    UserResponseDTO.UserProfileResponse getUserProfile(Long userId);

}
