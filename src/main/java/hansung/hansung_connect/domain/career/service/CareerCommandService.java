package hansung.hansung_connect.domain.career.service;

import hansung.hansung_connect.domain.career.dto.CareerRequestDTO;
import hansung.hansung_connect.domain.career.dto.CareerRequestDTO.BatchCreateRequestDTO;
import hansung.hansung_connect.domain.career.dto.CareerResponseDTO;

public interface CareerCommandService {
    CareerResponseDTO.CreateResponseDTO createCareer(CareerRequestDTO.CreateRequestDTO requestDTO);

    CareerResponseDTO.BulkCreateResponseDTO createCareers(BatchCreateRequestDTO requestDTO);

    CareerResponseDTO.UpdateResponseDTO updateCareer(Long careerId, CareerRequestDTO.UpdateRequestDTO requestDTO);

}

