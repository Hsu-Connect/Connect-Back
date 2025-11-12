package hansung.hansung_connect.domain.career.service;

import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_NOT_FOUND;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.USER_NOT_FOUND;

import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.domain.career.converter.CareerConverter;
import hansung.hansung_connect.domain.career.dto.CareerResponseDTO;
import hansung.hansung_connect.domain.career.entity.Career;
import hansung.hansung_connect.domain.career.repository.CareerRepository;
import hansung.hansung_connect.domain.user.entity.User;
import hansung.hansung_connect.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerQueryServiceImpl implements CareerQueryService {

    private final CareerRepository careerRepository;
    private final CareerConverter careerConverter;
    private final UserRepository userRepository;

    @Override
    public CareerResponseDTO.CreateResponseDTO getCareer(Long careerId) {
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new GeneralException(CAREER_NOT_FOUND));

        return careerConverter.toResponseDTO(career);
    }

    @Override
    public List<CareerResponseDTO.CreateResponseDTO> getMyCareers(Long currentUserId) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));

        List<Career> careers = careerRepository.findAllByUser_Id(user.getId());
        return careerConverter.toCreateResponseDTOList(careers);
    }
}

