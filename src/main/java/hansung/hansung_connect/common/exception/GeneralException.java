package hansung.hansung_connect.common.exception;

import hansung.hansung_connect.common.exception.code.BaseErrorCode;
import hansung.hansung_connect.common.exception.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    // 에러 상태 및 메시지만 담은 DTO 반환
    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    // HTTP 상태 코드까지 포함한 DTO 반환
    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
