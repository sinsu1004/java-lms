package nextstep.image.domain;

public class Image {
    private Long id;

    private int width;

    private int height;

    private int size;

    private ImageType type;

    public Image(int size, int width, int height, String type) {
        this.size = size;
        this.width = width;
        this.height = height;
        this.type = ImageType.from(type);
    }
}
