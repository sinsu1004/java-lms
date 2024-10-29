package nextstep.image.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ImageTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"gif", "jpg", "jpeG", "pNg", "svg"})
    void create(String type) {
        assertThat(ImageType.from(type)).isEqualTo(ImageType.valueOf(type.toUpperCase()));
    }

    @DisplayName("지원하지 않는 타입인 경우 예외가 발생한다.")
    @Test
    void throwExceptionWhenCreatingWithOtherType() {
        assertThatThrownBy(() -> ImageType.from("docs"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원하지 않는 이미지 타입입니다.");
    }
}