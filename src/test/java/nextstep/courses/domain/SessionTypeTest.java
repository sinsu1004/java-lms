package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionTypeTest {

    @DisplayName("유료 세션은 정상적인 수강 정원과 수강료로 생성할 수 있다")
    @Test
    void validateValidPaidSession() {
        SessionType.PAID.validate(100, 50000L);
    }

    @DisplayName("유료 세션은 올바르지 않은 수강 정원으로 생성할 수 없다")
    @ParameterizedTest
    @MethodSource("invalidPaidMaxStudentCountParameters")
    void validateInvalidPaidMaxStudentCount(Integer maxStudentCount) {
        assertThatThrownBy(() -> SessionType.PAID.validate(maxStudentCount, 50000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유료 강의는 최대 수강 인원을 설정해야 합니다.");
    }

    private static Stream<Arguments> invalidPaidMaxStudentCountParameters() {
        return Stream.of(
                Arguments.of((Integer) null),
                Arguments.of(0),
                Arguments.of(-1)
        );
    }

    @DisplayName("유료 세션은 올바르지 않은 수강료로 생성할 수 없다")
    @ParameterizedTest
    @MethodSource("invalidPaidPriceParameters")
    void validateInvalidPaidPrice(Long price) {
        assertThatThrownBy(() -> SessionType.PAID.validate(100, price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유료 강의는 수강료를 설정해야 합니다.");
    }

    private static Stream<Arguments> invalidPaidPriceParameters() {
        return Stream.of(
                Arguments.of((Long) null),
                Arguments.of(0L),
                Arguments.of(-1L)
        );
    }

    @Test
    @DisplayName("무료 세션은 수강 정원과 수강료가 0일때 생성할 수 있다")
    void validateValidFreeSession() {
        SessionType.FREE.validate(0, 0L);
    }

    @Test
    @DisplayName("무료 세션은 수강 정원을 설정할 수 없다")
    void validateFreeMaxStudentCount() {
        assertThatThrownBy(() -> SessionType.FREE.validate(1, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("무료 강의는 최대 수강 인원 제한을 설정할 수 없습니다.");
    }

    @DisplayName("무료 세션은 수강료를 설정할 수 없다")
    @ParameterizedTest
    @MethodSource("invalidFreePriceParameters")
    void validateFreePrice(Long price) {
        assertThatThrownBy(() -> SessionType.FREE.validate(0, price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("무료 강의는 수강료를 설정할 수 없습니다.");
    }

    private static Stream<Arguments> invalidFreePriceParameters() {
        return Stream.of(
                Arguments.of(1L),
                Arguments.of(1000L),
                Arguments.of(Long.MAX_VALUE)
        );
    }
}