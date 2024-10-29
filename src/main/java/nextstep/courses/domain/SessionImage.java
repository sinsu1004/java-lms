package nextstep.courses.domain;

import nextstep.image.domain.Image;

class SessionImage {
    private final int MAX_SIZE_KB = 1024;
    private final int MIN_WIDTH = 300;
    private final int MIN_HEIGHT = 200;
    private final double REQUIRED_RATIO = 1.5;

    Image image;

    public SessionImage(int size, int width, int height, String type) {
        validateSize(size);
        validateDimensions(width, height);
        validateAspectRatio(width, height);

        this.image = new Image(size, width, height, type);
    }

    private void validateSize(int size) {
        if (size > MAX_SIZE_KB) {
            throw new IllegalArgumentException(String.format("이미지 크기는 %dKB 이하여야 합니다.", MAX_SIZE_KB));
        }
    }

    private void validateDimensions(int width, int height) {
        if (width < MIN_WIDTH) {
            throw new IllegalArgumentException(String.format("이미지 width는 %d픽셀 이상이여야 합니다.", MIN_WIDTH));
        }
        if (height < MIN_HEIGHT) {
            throw new IllegalArgumentException(String.format("이미지 height는 %d픽셀 이상이여야 합니다.", MIN_HEIGHT));
        }
    }

    private void validateAspectRatio(int width, int height) {
        if ((double) width / height != REQUIRED_RATIO) {
            throw new IllegalArgumentException(String.format("width / height가 %d 이어야 합니다.", REQUIRED_RATIO));
        }
    }
}
