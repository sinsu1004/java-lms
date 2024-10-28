package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionSchedule {
    private final LocalDateTime startedAt;
    private final LocalDateTime endedAt;

    public SessionSchedule(LocalDateTime startedAt, LocalDateTime endedAt) {
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }
}
