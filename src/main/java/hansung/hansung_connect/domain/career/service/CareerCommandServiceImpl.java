package hansung.hansung_connect.domain.career.service;

import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_BULK_EMPTY;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_COMPANY_REQUIRED;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_END_YM_BEFORE_START;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_END_YM_FORBIDDEN_WHEN_EMPLOYED;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_END_YM_REQUIRED_WHEN_NOT_EMPLOYED;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_FORBIDDEN;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_INVALID_YEARMONTH_FORMAT;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_JOBTYPE_REQUIRED;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_NOT_FOUND;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_POSITION_REQUIRED;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_START_YM_REQUIRED;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.USER_NOT_FOUND;

import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.domain.career.converter.CareerConverter;
import hansung.hansung_connect.domain.career.dto.CareerRequestDTO;
import hansung.hansung_connect.domain.career.dto.CareerRequestDTO.BatchCreateRequestDTO;
import hansung.hansung_connect.domain.career.dto.CareerResponseDTO;
import hansung.hansung_connect.domain.career.entity.Career;
import hansung.hansung_connect.domain.career.repository.CareerRepository;
import hansung.hansung_connect.domain.user.entity.User;
import hansung.hansung_connect.domain.user.repository.UserRepository;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CareerCommandServiceImpl implements CareerCommandService {

    private final CareerRepository careerRepository;
    private final CareerConverter careerConverter;
    private final UserRepository userRepository;

    @Override
    public CareerResponseDTO.CreateResponseDTO createCareer(CareerRequestDTO.CreateRequestDTO requestDTO) {
        validateBusiness(requestDTO);

        Long currentUserId = 1L; // TODO: 추후 SecurityContext로 교체
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));

        Career saved = careerRepository.save(careerConverter.toEntity(requestDTO, user));
        return careerConverter.toResponseDTO(saved);
    }

    @Override
    public CareerResponseDTO.BulkCreateResponseDTO createCareers(BatchCreateRequestDTO requestDTO) {
        if (requestDTO == null || requestDTO.getItems() == null || requestDTO.getItems().isEmpty()) {
            throw new GeneralException(CAREER_BULK_EMPTY);
        }

        // 각 항목 검증
        requestDTO.getItems().forEach(this::validateBusiness);

        Long currentUserId = 1L; // TODO: 추후 SecurityContext로 교체
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));

        List<Career> toSave = careerConverter.toEntities(requestDTO.getItems(), user);
        List<Career> saved = careerRepository.saveAll(toSave);

        return careerConverter.toBulkCreateResponseDTO(saved);
    }

    // 커리어 수정(전체 대체)
    @Override
    public CareerResponseDTO.UpdateResponseDTO updateCareer(Long careerId,
                                                            CareerRequestDTO.UpdateRequestDTO requestDTO) {
        validateBusiness(requestDTO);

        // TODO: SecurityContext로 대체
        Long currentUserId = 1L;
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));

        // 커리어 조회, 소유자 검증
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new GeneralException(CAREER_NOT_FOUND));

        if (!career.getUser().getId().equals(user.getId())) {
            throw new GeneralException(CAREER_FORBIDDEN); // 본인 소유 아님
        }

        careerConverter.applyUpdate(career, requestDTO);

        return careerConverter.toUpdateResponseDTO(career);
    }

    // ====== 비즈니스 검증 (Update 전용) ======
    private void validateBusiness(CareerRequestDTO.UpdateRequestDTO dto) {
        if (dto.getCompanyName() == null || dto.getCompanyName().isBlank()) {
            throw new GeneralException(CAREER_COMPANY_REQUIRED);
        }
        if (dto.getPosition() == null || dto.getPosition().isBlank()) {
            throw new GeneralException(CAREER_POSITION_REQUIRED);
        }
        if (dto.getJobType() == null) {
            throw new GeneralException(CAREER_JOBTYPE_REQUIRED);
        }
        if (dto.getStartYm() == null || dto.getStartYm().isBlank()) {
            throw new GeneralException(CAREER_START_YM_REQUIRED);
        }

        YearMonth start = parseYm(dto.getStartYm());
        YearMonth end = dto.isEmployed() ? null : parseYmNullable(dto.getEndYm());

        if (dto.isEmployed() && dto.getEndYm() != null) {
            throw new GeneralException(CAREER_END_YM_FORBIDDEN_WHEN_EMPLOYED);
        }
        if (!dto.isEmployed()) {
            if (end == null) {
                throw new GeneralException(CAREER_END_YM_REQUIRED_WHEN_NOT_EMPLOYED);
            }
            if (end.isBefore(start)) {
                throw new GeneralException(CAREER_END_YM_BEFORE_START);
            }
        }
    }

    // ===== 도메인 규칙 검증 (단건/배치 공용) =====
    private void validateBusiness(CareerRequestDTO.CreateRequestDTO dto) {
        if (dto.getCompanyName() == null || dto.getCompanyName().isBlank()) {
            throw new GeneralException(CAREER_COMPANY_REQUIRED);
        }
        if (dto.getPosition() == null || dto.getPosition().isBlank()) {
            throw new GeneralException(CAREER_POSITION_REQUIRED);
        }
        if (dto.getJobType() == null) {
            throw new GeneralException(CAREER_JOBTYPE_REQUIRED);
        }
        if (dto.getStartYm() == null || dto.getStartYm().isBlank()) {
            throw new GeneralException(CAREER_START_YM_REQUIRED);
        }

        YearMonth start = parseYm(dto.getStartYm());
        YearMonth end = dto.isEmployed() ? null : parseYmNullable(dto.getEndYm());

        if (dto.isEmployed() && dto.getEndYm() != null) {
            throw new GeneralException(CAREER_END_YM_FORBIDDEN_WHEN_EMPLOYED);
        }
        if (!dto.isEmployed()) {
            if (end == null) {
                throw new GeneralException(CAREER_END_YM_REQUIRED_WHEN_NOT_EMPLOYED);
            }
            if (end.isBefore(start)) {
                throw new GeneralException(CAREER_END_YM_BEFORE_START);
            }
        }
    }

    private YearMonth parseYm(String raw) {
        try {
            return YearMonth.parse(raw.replace('.', '-').trim());
        } catch (Exception e) {
            throw new GeneralException(CAREER_INVALID_YEARMONTH_FORMAT);
        }
    }

    private YearMonth parseYmNullable(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return YearMonth.parse(raw.replace('.', '-').trim());
        } catch (Exception e) {
            throw new GeneralException(CAREER_INVALID_YEARMONTH_FORMAT);
        }
    }
}

