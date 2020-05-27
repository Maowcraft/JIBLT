package maowcraft.jiblt;

import maowcraft.jiblt.util.ConfigUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        if (!Files.exists(Paths.get("config.json"))) {
            try {
                Files.createFile(Paths.get("config.json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (args[0].length() > 0 && args[1].length() > 0) {
            if (args[0].equals("addabove") || args[0].equals("addbelow")) {
                try {
                    ConfigUtils.readConfigFile(Paths.get("config.json"));
                    Path activeMainLayer = ConfigUtils.getActiveMainLayer();
                    Path newTopLayer = Paths.get(args[1]);
                    BufferedImage topLayer = ImageIO.read(newTopLayer.toFile());

                    List<File> files = new ArrayList<>();
                    getAllFiles(activeMainLayer.toFile(), files);

                    for (File file : files) {
                        BufferedImage mainLayer = ImageIO.read(file);
                        drawPrerequisite(mainLayer, topLayer, args, file);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (args[0].equals("setmainlayer")) {
                ConfigUtils.setActiveMainLayer(new File(args[1]));
                ConfigUtils.writeConfigFile(args[1], Paths.get("config.json"));
                System.out.println("Main layer set to " + ConfigUtils.getActiveMainLayer());
            }
        } else {
            System.out.println("Missing arguments!");
        }
    }

    private static void getAllFiles(File dir, List<File> files) {
        File[] fList = dir.listFiles();
        if(fList != null) {
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    getAllFiles(file, files);
                }
            }
        }
    }

    private static void drawPrerequisite(BufferedImage mainLayer, BufferedImage topLayer, String[] args, File file) {
        if (mainLayer != null) {
            if (args[0].equals("addabove")) {
                draw(Paths.get(file.getPath()), mainLayer, topLayer);
            } else {
                draw(Paths.get(file.getPath()), topLayer, mainLayer);
            }
        }
    }

    private static void draw(Path filePath, BufferedImage bottomLayer, BufferedImage topLayer) {
        final BufferedImage newImage = new BufferedImage(bottomLayer.getWidth(), bottomLayer.getHeight(), BufferedImage.TYPE_INT_ARGB);
        final Graphics graphics = newImage.getGraphics();

        graphics.drawImage(bottomLayer, 0, 0, null);
        graphics.drawImage(topLayer, 0, 0, null);
        try {
            write(filePath, newImage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void write(Path filePath, BufferedImage image) throws IOException {
        Path images = Paths.get("input");
        if (!Files.exists(images)) {
            Files.createDirectory(images);
        }
        for (File ignored : Objects.requireNonNull(images.toFile().listFiles())) {
            File outputFile = new File("output/" + filePath.toFile().getName());
            if (!outputFile.exists()) {
                if (!outputFile.getParentFile().exists()) {
                    outputFile.getParentFile().mkdirs();
                }
                outputFile.createNewFile();
            }
            ImageIO.write(image, "png", outputFile);
        }
    }
}
