package maowcraft.jiblt;

import maowcraft.jiblt.util.ImageWithName;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {
    public static Path TOP_LAYER;
    public static Path BOTTOM_LAYERS;
    public static Path OUTPUT;

    public static void main(String[] args) {
        if (args.length > 2) {
            TOP_LAYER = Paths.get(args[0]);
            BOTTOM_LAYERS = Paths.get(args[1]);
            OUTPUT = Paths.get(args[2]);

            if (Files.notExists(TOP_LAYER) || Files.notExists(BOTTOM_LAYERS) || Files.notExists(OUTPUT)) {
                System.err.println("A specified path could not be found.");
                return;
            }

            ThreadGroup threadGroup = new ThreadGroup("jiblt");
            int totalProcessors = Runtime.getRuntime().availableProcessors();
            int maxThreads = 4;

            List<File> layerFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(BOTTOM_LAYERS.toFile().listFiles())));

            List<ImageWithName> images = new ArrayList<>();
            for (File file : layerFiles) images.add(new ImageWithName(read(file), file.getName()));

            List<ImageLayerer> layerers = new ArrayList<>();
            for (int i = 0; i < maxThreads; i++) layerers.add(new ImageLayerer("Image Layerer " + i, threadGroup, read(TOP_LAYER.toFile()), images));

            for (int i = 0; i < layerers.size(); i++) {
                if (threadGroup.activeCount() < totalProcessors) {
                    ImageLayerer layerer = layerers.get(i);
                    layerer.start();
                    i++;
                }
            }
        } else {
            System.err.println("Missing arguments.\nSyntax: program.jar <top layer(s)> <bottom layer> <output path>");
        }
    }

    private static BufferedImage read(File file)  {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
