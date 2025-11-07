package hansung.hansung_connect.domain.career.service;

import hansung.hansung_connect.domain.career.dto.CareerResponseDTO;
import hansung.hansung_connect.domain.career.dto.CareerResponseDTO.CreateResponseDTO;
import java.util.List;

public interface CareerQueryService {
    CareerResponseDTO.CreateResponseDTO getCareer(Long careerId);

    List<CreateResponseDTO> getMyCareers();

}

