package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class SessionPolicy {
    private final SessionType type;
    private final int maxStudentCount;
    private final Long price;

    public SessionPolicy(SessionType type, int maxStudentCount, Long price) {
        validate(type, maxStudentCount, price);
        this.type = type;
        this.maxStudentCount = maxStudentCount;
        this.price = price;

    }
    private void validate(SessionType type, int maxStudentCount, Long price) {
        type.validate(maxStudentCount, price);
    }

    public void validateEnrollment(int currentStudentCount, Payment payment) {
        if (type == SessionType.PAID) {
            validatePaidEnrollment(currentStudentCount, payment);
        }
    }

    private void validatePaidEnrollment(int currentStudentCount, Payment payment) {
        if (currentStudentCount >= maxStudentCount) {
            throw new IllegalStateException("수강 인원이 초과되었습니다.");
        }
        if (!payment.canEnroll(price)) {
            throw new IllegalArgumentException("결제 금액이 수강료와 일치하지 않습니다.");
        }
    }

    public SessionType getType() {
        return type;
    }

    public int getMaxStudentCount() {
        return maxStudentCount;
    }

    public Long getPrice() {
        return price;
    }
}
