package hansung.hansung_connect.domain.user.service;

import hansung.hansung_connect.domain.user.dto.UserRequestDTO;

public interface UserCommandService {
    void updateMyBasicProfile(Long currentUserId, UserRequestDTO.UpdateBasicProfileRequest request);
}
