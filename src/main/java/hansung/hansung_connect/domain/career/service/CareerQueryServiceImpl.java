package hansung.hansung_connect.domain.career.service;

import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_NOT_FOUND;

import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.domain.career.converter.CareerConverter;
import hansung.hansung_connect.domain.career.dto.CareerResponseDTO;
import hansung.hansung_connect.domain.career.entity.Career;
import hansung.hansung_connect.domain.career.repository.CareerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerQueryServiceImpl implements CareerQueryService {

    private final CareerRepository careerRepository;
    private final CareerConverter careerConverter;

    @Override
    public CareerResponseDTO.CreateResponseDTO getCareer(Long careerId) {
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new GeneralException(CAREER_NOT_FOUND));
        
        return careerConverter.toResponseDTO(career);
    }
}

