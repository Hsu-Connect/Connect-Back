package hansung.hansung_connect.common.exception.code.status;

import hansung.hansung_connect.common.exception.code.BaseErrorCode;
import hansung.hansung_connect.common.exception.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "해당 리소스를 찾을 수 없습니다."),

    // For test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "테스트용 에러메세지입니다."),

    // 유저 관련 응답
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "사용자를 찾을 수 없습니다."),

    // 게시글 관련 응답
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "POST4001", "게시글을 찾을 수 없습니다."),
    POST_QUERY_TYPE_EXCEPTION(HttpStatus.BAD_REQUEST, "POST4002", "게시글 조회 타입이 잘못되었습니다."),
    POST_LIST_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "POST4003", "게시글 리스트가 비어있습니다."),
    INVALID_PAGE_FOR_POPULAR(HttpStatus.BAD_REQUEST, "POST4004", "인기 게시글은 0번 페이지만 조회 가능합니다."),
    POST_FORBIDDEN(HttpStatus.BAD_REQUEST, "POST4005", "해당 게시글의 권한이 없습니다."),

    //댓글 관련 응답
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST,"COMMENT4001","댓글을 찾을 수 없습니다."),
    COMMENT_FORBIDDEN(HttpStatus.BAD_REQUEST,"COMMENT4002","해당 댓글의 권한이 없습니다."),

    // 커리어 관련 응답
    CAREER_COMPANY_REQUIRED(HttpStatus.BAD_REQUEST, "CAREER4001", "회사명은 필수입니다."),
    CAREER_POSITION_REQUIRED(HttpStatus.BAD_REQUEST, "CAREER4002", "직무명은 필수입니다."),
    CAREER_JOBTYPE_REQUIRED(HttpStatus.BAD_REQUEST, "CAREER4003", "재직 형태는 필수입니다."),
    CAREER_START_YM_REQUIRED(HttpStatus.BAD_REQUEST, "CAREER4004", "근무 시작 연월은 필수입니다."),
    CAREER_INVALID_YEARMONTH_FORMAT(HttpStatus.BAD_REQUEST, "CAREER4005", "연월 형식이 올바르지 않습니다. (예: 2024-04)"),
    CAREER_END_YM_FORBIDDEN_WHEN_EMPLOYED(HttpStatus.BAD_REQUEST, "CAREER4006", "재직중이면 종료 연월을 입력할 수 없습니다."),
    CAREER_END_YM_REQUIRED_WHEN_NOT_EMPLOYED(HttpStatus.BAD_REQUEST, "CAREER4007", "재직중이 아니면 종료 연월이 필요합니다."),
    CAREER_END_YM_BEFORE_START(HttpStatus.BAD_REQUEST, "CAREER4008", "종료 연월은 시작 연월보다 앞설 수 없습니다."),
    CAREER_BULK_EMPTY(HttpStatus.BAD_REQUEST, "CAREER4009", "추가할 커리어 항목이 비어 있습니다."),
    CAREER_NOT_FOUND(HttpStatus.BAD_REQUEST, "CAREER4007", "해당 커리어를 찾을 수 없습니다."),
    CAREER_FORBIDDEN(HttpStatus.FORBIDDEN, "CAREER4008", "해당 커리어에 대한 수정 권한이 없습니다."),

    // 링크 관련 응답
    LINK_NOT_FOUND(HttpStatus.BAD_REQUEST, "LINK4001", "해당 링크를 찾을 수 없습니다."),
    LINK_FORBIDDEN(HttpStatus.FORBIDDEN, "LINK4031", "해당 링크에 대한 접근 권한이 없습니다."),
    LINK_TYPE_REQUIRED(HttpStatus.BAD_REQUEST, "LINK4002", "링크 타입은 필수입니다."),
    LINK_URL_REQUIRED(HttpStatus.BAD_REQUEST, "LINK4003", "URL은 비어 있을 수 없습니다."),
    LINK_INVALID_URL(HttpStatus.BAD_REQUEST, "LINK4004", "유효한 URL 형식이 아닙니다."),
    LINK_DUPLICATE_TYPE(HttpStatus.BAD_REQUEST, "LINK4005", "동일한 타입의 링크가 이미 존재합니다."),
    LINK_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "LINK5001", "링크 수정 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
