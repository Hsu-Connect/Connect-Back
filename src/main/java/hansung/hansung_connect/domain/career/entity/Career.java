package hansung.hansung_connect.domain.career.entity;

import hansung.hansung_connect.domain.career.converter.YearMonthAttributeConverter;
import hansung.hansung_connect.domain.career.entity.enums.JobType;
import hansung.hansung_connect.domain.user.entity.User;
import hansung.hansung_connect.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.YearMonth;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Career extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String companyName;

    @Column(length = 50, nullable = false)
    private String position;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType jobType;

    @Column(nullable = false)
    private boolean isEmployed;

    @Convert(converter = YearMonthAttributeConverter.class)
    @Column(name = "start_ym", nullable = false, length = 7) // 시작연월 "2024-04"
    private YearMonth startYm;

    @Convert(converter = YearMonthAttributeConverter.class)
    @Column(name = "end_ym", length = 7) // 재직중이면 null
    private YearMonth endYm;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void update(String companyName,
                       String position,
                       JobType jobType,
                       boolean employed,
                       YearMonth startYm,
                       YearMonth endYm) {
        this.companyName = companyName;
        this.position = position;
        this.jobType = jobType;
        this.isEmployed = employed;
        this.startYm = startYm;
        this.endYm = endYm;
    }
}
