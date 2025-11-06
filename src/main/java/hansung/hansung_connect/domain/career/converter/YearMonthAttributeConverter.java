package hansung.hansung_connect.domain.career.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.YearMonth;

// 년-월(YYYY-MM) <-> String 컬럼 변환 컨버터
@Converter(autoApply = true)
public class YearMonthAttributeConverter implements AttributeConverter<YearMonth, String> {
    @Override
    public String convertToDatabaseColumn(YearMonth attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toString(); // 예: "2024-04"
    }

    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return YearMonth.parse(dbData);
    }

}

