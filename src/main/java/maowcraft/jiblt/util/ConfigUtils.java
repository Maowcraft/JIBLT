package maowcraft.jiblt.util;

import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigUtils {
    private static Path activeMainLayer = null;

    public static void writeConfigFile(String path, Path writePath) {
        try {
            FileWriter writer = new FileWriter(writePath.toFile());
            new GsonBuilder().setPrettyPrinting().create().toJson(path, writer);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void readConfigFile(Path readPath) {
        try {
            FileReader reader = new FileReader(readPath.toFile());
            activeMainLayer = Paths.get(new GsonBuilder().create().fromJson(reader, String.class));
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Path getActiveMainLayer() {
        return activeMainLayer;
    }

    public static void setActiveMainLayer(File activeMainLayer) {
        ConfigUtils.activeMainLayer = activeMainLayer.toPath();
    }
}
