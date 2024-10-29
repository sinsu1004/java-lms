package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionPolicyTest {

    @DisplayName("무료 강의 정책을 생성할 수 있다")
    @Test
    void createFreePolicy() {
        SessionPolicy policy = new SessionPolicy(SessionType.FREE, 0, 0L);

        assertThat(policy.getType()).isEqualTo(SessionType.FREE);
        assertThat(policy.getMaxStudentCount()).isZero();
        assertThat(policy.getPrice()).isZero();
    }

    @DisplayName("유료 강의 정책을 생성할 수 있다")
    @Test
    void createPaidPolicy() {
        SessionPolicy policy = new SessionPolicy(SessionType.PAID, 100, 50000L);

        assertThat(policy.getType()).isEqualTo(SessionType.PAID);
        assertThat(policy.getMaxStudentCount()).isEqualTo(100);
        assertThat(policy.getPrice()).isEqualTo(50000L);
    }

    @DisplayName("유료 강의는 결제 금액이 수강료와 일치하지 않으면 수강 신청할 수 없다")
    @Test
    void validateEnrollmentWithInvalidPayment() {
        SessionPolicy policy = new SessionPolicy(SessionType.PAID, 100, 50000L);
        Payment payment = new Payment("id", 1L, 1L, 40000L);

        assertThatThrownBy(() -> policy.validateEnrollment(0, payment))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("결제 금액이 수강료와 일치하지 않습니다.");
    }

    @DisplayName("유료 강의는 최대 수강 인원을 초과하여 수강 신청할 수 없다")
    @Test
    void validateEnrollmentWithFullCapacity() {
        int maxCount = 100;
        SessionPolicy policy = new SessionPolicy(SessionType.PAID, maxCount, 50000L);
        Payment payment = new Payment("id", 1L, 1L, 50000L);

        assertThatThrownBy(() -> policy.validateEnrollment(maxCount, payment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("수강 인원이 초과되었습니다.");
    }

}


