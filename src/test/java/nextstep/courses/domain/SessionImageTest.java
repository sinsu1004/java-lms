package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionImageTest {
    public static final SessionImage SESSION_IMAGE = new SessionImage(1000,300,200,"jpg");

    @DisplayName("올바른 이미지 정보로 SessionImage를 생성할 수 있다")
    @Test
    void create() {
        int size = 1000;
        int width = 300;
        int height = 200;
        String type = "jpg";

        SessionImage sessionImage = new SessionImage(size, width, height, type);

        assertThat(sessionImage.image).isNotNull();
    }

    @DisplayName("이미지 크기가 제한을 초과하면 예외가 발생한다")
    @ParameterizedTest
    @CsvSource({
            "1025, 300, 200",
            "2048, 300, 200",
            "5000, 300, 200"
    })
    void validateSize(int size, int width, int height) {
        assertThatThrownBy(() -> new SessionImage(size, width, height, "jpg"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이미지 크기가 최소 제한에 미달하면 예외가 발생한다")
    @ParameterizedTest
    @CsvSource({
            "1000, 299, 200",
            "1000, 300, 199",
            "1000, 299, 199"
    })
    void validateDimensions(int size, int width, int height) {
        assertThatThrownBy(() -> new SessionImage(size, width, height, "jpg"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이미지 비율이 3:2가 아니면 예외가 발생한다")
    @ParameterizedTest
    @CsvSource({
            "1000, 300, 201",
            "1000, 301, 200",
            "1000, 400, 300"
    })
    void validateAspectRatio(int size, int width, int height) {
        assertThatThrownBy(() -> new SessionImage(size, width, height, "jpg"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}