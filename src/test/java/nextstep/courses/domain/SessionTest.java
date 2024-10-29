package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static nextstep.courses.domain.SessionImageTest.SESSION_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionTest {
    private static final LocalDateTime START_TIME = LocalDateTime.now();
    private static final LocalDateTime END_TIME = START_TIME.plusDays(7);

    @DisplayName("무료 강의를 생성할 수 있다")
    @Test
    void createFreeSession() {
        Session session = Session.ofFree(SESSION_IMAGE, SessionState.PREPARING, 0, 0L, START_TIME, END_TIME);

        assertThat(session.getType()).isEqualTo(SessionType.FREE);
        assertThat(session.getMaxStudentCount()).isZero();
        assertThat(session.getPrice()).isZero();
    }

    @DisplayName("유료 강의를 생성할 수 있다")
    @Test
    void createPaidSession() {
        int maxCount = 100;
        long price = 50000L;

        Session session = Session.ofPaid(SESSION_IMAGE, SessionState.PREPARING, maxCount, price, START_TIME, END_TIME);

        assertThat(session.getType()).isEqualTo(SessionType.PAID);
        assertThat(session.getMaxStudentCount()).isEqualTo(maxCount);
        assertThat(session.getPrice()).isEqualTo(price);
    }

    @DisplayName("무료 강의는 수강 인원 제한과 가격을 설정할 수 없다")
    @ParameterizedTest
    @MethodSource("invalidFreeSessionParameters")
    void validateFreeSession(int maxCount, Long price, String expectedMessage) {
        assertThatThrownBy(() ->
                Session.ofFree(SESSION_IMAGE, SessionState.PREPARING, maxCount, price, START_TIME, END_TIME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);
    }

    private static Stream<Arguments> invalidFreeSessionParameters() {
        return Stream.of(
                Arguments.of(100, 0L, "무료 강의는 최대 수강 인원 제한을 설정할 수 없습니다"),
                Arguments.of(0, 50000L, "무료 강의는 수강료를 설정할 수 없습니다")
        );
    }

    @DisplayName("유료 강의는 수강 인원 제한과 가격을 반드시 설정해야 한다")
    @ParameterizedTest
    @MethodSource("invalidPaidSessionParameters")
    void validatePaidSession(int maxCount, Long price, String expectedMessage) {
        assertThatThrownBy(() ->
                Session.ofPaid(SESSION_IMAGE, SessionState.PREPARING, maxCount, price, START_TIME, END_TIME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);
    }

    private static Stream<Arguments> invalidPaidSessionParameters() {
        return Stream.of(
                Arguments.of(0, 50000L, "유료 강의는 최대 수강 인원을 설정해야 합니다"),
                Arguments.of(-1, 50000L, "유료 강의는 최대 수강 인원을 설정해야 합니다"),
                Arguments.of(100, 0L, "유료 강의는 수강료를 설정해야 합니다"),
                Arguments.of(100, -1L, "유료 강의는 수강료를 설정해야 합니다"),
                Arguments.of(100, null, "유료 강의는 수강료를 설정해야 합니다")
        );
    }

    @Test
    void 강의_상태가_모집중이_아니면_수강신청_실패() {
        Session session = Session.ofFree(SESSION_IMAGE, SessionState.PREPARING, 0, 0L, START_TIME, END_TIME);
        Payment payment = new Payment();

        assertThatThrownBy(() -> session.enroll(payment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("모집 중인 강의만 수강 신청 가능합니다");
    }

    @Test
    void 유료_강의_결제금액이_수강료와_일치하지_않으면_수강신청_실패() {
        Session session = Session.ofPaid(SESSION_IMAGE, SessionState.RECRUITING, 100, 50000L, START_TIME, END_TIME);
        Payment payment = new Payment("", 0L, 0L, 0L);

        assertThatThrownBy(() -> session.enroll(payment))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("결제 금액이 수강료와 일치하지 않습니다");
    }

    @Test
    void 유료_강의_최대_수강_인원이_초과하면_수강신청_실패() {
        Session session = Session.ofPaid(SESSION_IMAGE, SessionState.RECRUITING, 1, 50000L, START_TIME, END_TIME);
        Payment payment = new Payment("", 0L, 0L, 50000L);

        session.enroll(payment);

        assertThatThrownBy(() -> session.enroll(payment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("수강 인원이 초과되었습니다");
    }

}