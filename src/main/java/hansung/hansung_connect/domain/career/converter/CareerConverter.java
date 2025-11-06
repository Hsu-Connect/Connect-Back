package hansung.hansung_connect.domain.career.converter;

import hansung.hansung_connect.domain.career.dto.CareerRequestDTO;
import hansung.hansung_connect.domain.career.dto.CareerRequestDTO.CreateRequestDTO;
import hansung.hansung_connect.domain.career.dto.CareerResponseDTO;
import hansung.hansung_connect.domain.career.dto.CareerResponseDTO.CreateResponseDTO;
import hansung.hansung_connect.domain.career.entity.Career;
import hansung.hansung_connect.domain.user.entity.User;
import java.time.YearMonth;
import java.util.List;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
@Component
public class CareerConverter {

    public Career toEntity(CreateRequestDTO dto, User user) {
        return Career.builder()
                .companyName(dto.getCompanyName())
                .position(dto.getPosition())
                .jobType(dto.getJobType())
                .isEmployed(dto.isEmployed())
                .startYm(parseYm(dto.getStartYm()))
                .endYm(parseYmNullable(dto.getEndYm()))
                .user(user)
                .build();
    }

    public CreateResponseDTO toResponseDTO(Career career) {
        return CreateResponseDTO.builder()
                .id(career.getId())
                .companyName(career.getCompanyName())
                .position(career.getPosition())
                .jobType(career.getJobType())
                .employed(career.isEmployed())
                .startYm(formatYm(career.getStartYm()))
                .endYm(formatYmNullable(career.getEndYm()))
                .build();
    }

    // ===== 배치 변환 =====
    public List<Career> toEntities(List<CareerRequestDTO.CreateRequestDTO> items, User user) {
        return items.stream()
                .map(dto -> toEntity(dto, user))
                .toList();
    }

    public List<CreateResponseDTO> toCreateResponseDTOList(List<Career> careers) {
        return careers.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CareerResponseDTO.BulkCreateResponseDTO toBulkCreateResponseDTO(List<Career> saved) {
        List<CareerResponseDTO.CreateResponseDTO> list = toCreateResponseDTOList(saved);
        return CareerResponseDTO.BulkCreateResponseDTO.builder()
                .careers(list)
                .createdCount(list.size())
                .build();
    }


    // ---- helpers ----
    private YearMonth parseYm(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("startYm은 필수입니다.");
        }
        String normalized = raw.replace('.', '-'); // "2024.04"도 허용
        return YearMonth.parse(normalized);        // "2024-04"
    }

    private YearMonth parseYmNullable(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        String normalized = raw.replace('.', '-');
        return YearMonth.parse(normalized);
    }

    private String formatYm(YearMonth ym) {
        return ym.toString(); // "yyyy-MM"
    }

    private String formatYmNullable(YearMonth ym) {
        return ym == null ? null : ym.toString();
    }
}

