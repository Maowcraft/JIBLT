package maowcraft.jiblt.util;

import java.awt.image.BufferedImage;

public class ImageWithName {
    private final BufferedImage image;
    private final String name;

    public ImageWithName(BufferedImage image, String name) {
        this.image = image;
        this.name = name;
    }

    public BufferedImage getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
}
