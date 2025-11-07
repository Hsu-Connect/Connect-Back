package hansung.hansung_connect.domain.career.service;

import hansung.hansung_connect.domain.career.dto.CareerResponseDTO;

public interface CareerQueryService {
    CareerResponseDTO.CreateResponseDTO getCareer(Long careerId);
}

