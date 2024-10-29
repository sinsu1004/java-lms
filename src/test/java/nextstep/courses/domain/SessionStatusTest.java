package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionStatusTest {

    @DisplayName("세션 상태 생성 시 수강생 수는 0명이다")
    @Test
    void createStatus() {
        SessionStatus status = new SessionStatus(SessionState.RECRUITING);

        int actual = status.getStudentCount();

        assertThat(actual).isZero();
    }

    @DisplayName("수강생 수를 증가시킬 수 있다")
    @Test
    void incrementStudentCount() {
        SessionStatus status = new SessionStatus(SessionState.RECRUITING);

        status.incrementStudentCount();
        int actual = status.getStudentCount();

        assertThat(actual).isEqualTo(1);
    }

    @DisplayName("RECRUITING 상태에서만 수강신청이 가능하다")
    @Test
    void validateRecruitingState() {
        SessionStatus status = new SessionStatus(SessionState.RECRUITING);

        status.validateStateForEnrollment();
    }

    @DisplayName("RECRUITING이 아닌 상태에서는 수강신청할 수 없다")
    @ParameterizedTest
    @EnumSource(value = SessionState.class, names = {"RECRUITING"}, mode = EnumSource.Mode.EXCLUDE)
    void validateNotRecruitingState(SessionState state) {
        SessionStatus status = new SessionStatus(state);

        assertThatThrownBy(status::validateStateForEnrollment)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("모집 중인 강의만 수강 신청 가능합니다.");
    }
}