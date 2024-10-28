package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionStateTest {

    @DisplayName("SessionState가 RECRUITING이면 true를 반환한다.")
    @Test
    void canEnroll() {
        SessionState state = SessionState.RECRUITING;

        boolean actual = state.canEnroll();

        assertTrue(actual);
    }

}