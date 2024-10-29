package nextstep.courses.domain;

import java.util.function.BiConsumer;

public enum SessionType {
    PAID((maxStudentCount, price) -> {
        if (maxStudentCount == null || maxStudentCount <= 0) {
            throw new IllegalArgumentException("유료 강의는 최대 수강 인원을 설정해야 합니다.");
        }
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("유료 강의는 수강료를 설정해야 합니다.");
        }
    }),
    FREE((maxStudentCount, price) -> {
        if (maxStudentCount > 0) {
            throw new IllegalArgumentException("무료 강의는 최대 수강 인원 제한을 설정할 수 없습니다.");
        }
        if (price != null && price > 0) {
            throw new IllegalArgumentException("무료 강의는 수강료를 설정할 수 없습니다.");
        }
    });

    private final BiConsumer<Integer, Long> validator;

    SessionType(BiConsumer<Integer, Long> validator) {
        this.validator = validator;
    }

    public void validate(Integer maxStudentCount, Long price) {
        validator.accept(maxStudentCount, price);
    }
}
