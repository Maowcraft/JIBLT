package maowcraft.jiblt;

import maowcraft.jiblt.util.ImageWithName;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageLayerer extends Thread {
    private final BufferedImage topLayer;
    private final List<ImageWithName> bottomLayers;

    public ImageLayerer(String name, ThreadGroup threadGroup, BufferedImage topLayer, List<ImageWithName> bottomLayers) {
        super(threadGroup, name);

        this.topLayer = topLayer;
        this.bottomLayers = bottomLayers;
    }

    @Override
    public void run() {
        for (ImageWithName image : bottomLayers) {
            draw(topLayer, image);
        }
    }

    private static void draw(BufferedImage topLayer, ImageWithName bottomLayer) {
        BufferedImage image = bottomLayer.getImage();
        final BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        final Graphics graphics = newImage.getGraphics();

        graphics.drawImage(image, 0, 0, null);
        graphics.drawImage(topLayer, 0, 0, null);

        try {
            write(bottomLayer.getName(), newImage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void write(String fileName, BufferedImage image) throws IOException {
        String outputPath = Main.OUTPUT.toAbsolutePath().toString();
        File outputFile = new File(outputPath + "/" + fileName);
        if (!outputFile.exists()) {
            outputFile.getParentFile().mkdirs();
            outputFile.createNewFile();
            ImageIO.write(image, "png", outputFile);
            System.out.println("Layered image written to file: " + outputPath + "/" + fileName);
        }
    }
}
