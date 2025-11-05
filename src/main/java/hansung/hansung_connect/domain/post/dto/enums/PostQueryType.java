package hansung.hansung_connect.domain.post.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.common.exception.code.status.ErrorStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 조회 유형 [POPULAR, FREE, PROMOTION, NOTICE]")
public enum PostQueryType {
    POPULAR,
    FREE,
    PROMOTION,
    NOTICE;

    @JsonCreator
    public static PostQueryType from(String value) {
        if(value == null) {
            return null;
        }
        try {
            return PostQueryType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new GeneralException(ErrorStatus.POST_QUERY_TYPE_EXCEPTION);
        }
    }

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase();
    }
}
