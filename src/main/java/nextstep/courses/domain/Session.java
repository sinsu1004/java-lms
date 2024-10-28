package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;

public class Session {
    private Long id;

    private SessionImage image;

    private SessionStatus status;

    private SessionPolicy policy;

    private SessionSchedule schedule;

    public Session(Long id, SessionImage image, SessionType type, SessionState state, int maxStudentCount,
                   Long price, LocalDateTime startedAt, LocalDateTime endedAt) {
        validateSessionType(type, maxStudentCount, price);

        this.id = id;
        this.image = image;
        this.status = new SessionStatus(state);
        this.policy = new SessionPolicy(type, maxStudentCount, price);
        this.schedule = new SessionSchedule(startedAt, endedAt);
    }

    public static Session ofFree(SessionImage image, SessionState state, int maxStudentCount, Long price, LocalDateTime startedAt, LocalDateTime endedAt) {
        return new Session(
                0L,
                image,
                SessionType.FREE,
                state,
                maxStudentCount,
                price,
                startedAt,
                endedAt
        );
    }

    public static Session ofPaid(SessionImage image, SessionState state, int maxStudentCount, Long price, LocalDateTime startedAt, LocalDateTime endedAt) {
        return new Session(
                0L,
                image,
                SessionType.PAID,
                state,
                maxStudentCount,
                price,
                startedAt,
                endedAt
        );
    }

    private void validateSessionType(SessionType type, int maxStudentCount, Long price) {
        type.validate(maxStudentCount, price);
    }


    public void enroll(Payment payment) {
        policy.validateEnrollment(status.getStudentCount(), payment);
        status.validateStateForEnrollment();
        status.incrementStudentCount();
    }

    public SessionType getType() {
        return policy.getType();
    }

    public int getMaxStudentCount() {
        return policy.getMaxStudentCount();
    }

    public Long getPrice() {
        return policy.getPrice();
    }
}

