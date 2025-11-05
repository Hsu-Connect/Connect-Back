package hansung.hansung_connect.domain.user.entity.enums;

public enum AcademicStatus {
    ENROLLED("재학중"),   //재학 중
    GRADUATED("졸업"),  //졸업
    EXPECTED_GRADUATION("졸업예정"),    //졸업 예정
    COMPLETED("수료"),  //수료
    LEAVE_OF_ABSENCE("휴학");   //휴학

    private final String status;

    AcademicStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
