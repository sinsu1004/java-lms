package nextstep.courses.domain;

public enum SessionState {
    PREPARING("준비"),
    RECRUITING("모집중"),
    FINISHED("종료");

    private final String description;

    SessionState(String description) {
        this.description = description;
    }

    public boolean canEnroll() {
        return this == RECRUITING;
    }

}
