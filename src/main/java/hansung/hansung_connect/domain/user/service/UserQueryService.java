package hansung.hansung_connect.domain.user.service;

import hansung.hansung_connect.domain.user.dto.UserResponseDTO;

public interface UserQueryService {
    UserResponseDTO.SummaryCardResponse getMySummaryCard(Long currentUserId);
}
