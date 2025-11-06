package hansung.hansung_connect.domain.career.service;

import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_COMPANY_REQUIRED;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_END_YM_BEFORE_START;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_END_YM_FORBIDDEN_WHEN_EMPLOYED;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_END_YM_REQUIRED_WHEN_NOT_EMPLOYED;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_INVALID_YEARMONTH_FORMAT;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_JOBTYPE_REQUIRED;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_POSITION_REQUIRED;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.CAREER_START_YM_REQUIRED;
import static hansung.hansung_connect.common.exception.code.status.ErrorStatus.USER_NOT_FOUND;

import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.domain.career.converter.CareerConverter;
import hansung.hansung_connect.domain.career.dto.CareerRequestDTO;
import hansung.hansung_connect.domain.career.dto.CareerResponseDTO;
import hansung.hansung_connect.domain.career.entity.Career;
import hansung.hansung_connect.domain.career.repository.CareerRepository;
import hansung.hansung_connect.domain.user.entity.User;
import hansung.hansung_connect.domain.user.repository.UserRepository;
import java.time.YearMonth;
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

        Long currentUserId = 1L; // TODO: SecurityContext로 교체
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));

        Career saved = careerRepository.save(careerConverter.toEntity(requestDTO, user));
        return careerConverter.toResponseDTO(saved); // ← 이 메서드도 이너 DTO 반환하도록 수정(아래 2번)
    }

    // 도메인 규칙 검증
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

        // 월 포맷 검증 (".", "-" 모두 허용)
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
            String n = raw.replace('.', '-');
            return YearMonth.parse(n);
        } catch (Exception e) {
            throw new GeneralException(CAREER_INVALID_YEARMONTH_FORMAT);
        }
    }

    private YearMonth parseYmNullable(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return YearMonth.parse(raw.replace('.', '-'));
        } catch (Exception e) {
            throw new GeneralException(CAREER_INVALID_YEARMONTH_FORMAT);
        }
    }
}

//@Service
//@RequiredArgsConstructor
//@Transactional
//public class CareerCommandServiceImpl implements CareerCommandService {
//
//    private final CareerRepository careerRepository;
//    private final CareerConverter careerConverter;
//    private final UserRepository userRepository; // /users/me 이므로 인증 사용자 로드
//
//    @Override
//    public CareerResponseDTO createCareer(CareerRequestDTO requestDTO) {
//        validateBusiness(requestDTO);
//
//        // TODO: 추후 SecurityContext에서 현재 로그인 사용자 ID 추출하도록 교체
//        Long currentUserId = 1L; // 임시
//        User user = userRepository.findById(currentUserId)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
//
//        Career saved = careerRepository.save(careerConverter.toEntity(requestDTO, user));
//        return careerConverter.toResponseDTO(saved);
//    }
//
//    // ---- 도메인 규칙 검증 ----
//    private void validateBusiness(CareerRequestDTO dto) {
//        if (dto.getCompanyName() == null || dto.getCompanyName().isBlank()) {
//            throw new IllegalArgumentException("회사명은 필수입니다.");
//        }
//        if (dto.getPosition() == null || dto.getPosition().isBlank()) {
//            throw new IllegalArgumentException("직무명은 필수입니다.");
//        }
//        if (dto.getJobType() == null) {
//            throw new IllegalArgumentException("재직 형태는 필수입니다.");
//        }
//        if (dto.getStartYm() == null || dto.getStartYm().isBlank()) {
//            throw new IllegalArgumentException("근무 시작 연월은 필수입니다.");
//        }
//
//        // 월 포맷 검증 (".", "-" 모두 허용)
//        YearMonth start = parseYm(dto.getStartYm());
//        YearMonth end = dto.isEmployed() ? null : parseYmNullable(dto.getEndYm());
//
//        if (dto.isEmployed() && dto.getEndYm() != null) {
//            throw new IllegalArgumentException("재직중이면 종료 연월을 입력할 수 없습니다.");
//        }
//        if (!dto.isEmployed()) {
//            if (end == null) {
//                throw new IllegalArgumentException("재직중이 아니면 종료 연월이 필요합니다.");
//            }
//            if (end.isBefore(start)) {
//                throw new IllegalArgumentException("종료 연월은 시작 연월보다 앞설 수 없습니다.");
//            }
//        }
//    }
//
//    private YearMonth parseYm(String raw) {
//        String n = raw.replace('.', '-');
//        return YearMonth.parse(n);
//    }
//
//    private YearMonth parseYmNullable(String raw) {
//        if (raw == null || raw.isBlank()) {
//            return null;
//        }
//        return YearMonth.parse(raw.replace('.', '-'));
//    }
//}
//
