package nextstep.courses.domain;

public class SessionStatus {
    private final SessionState state;
    private int studentCount;

    public SessionStatus(SessionState state) {
        this.state = state;
        this.studentCount = 0;
    }

    public void validateStateForEnrollment() {
        if (!state.canEnroll()) {
            throw new IllegalStateException("모집 중인 강의만 수강 신청 가능합니다.");
        }
    }

    public void incrementStudentCount() {
        studentCount++;
    }

    public int getStudentCount() {
        return studentCount;
    }
}
