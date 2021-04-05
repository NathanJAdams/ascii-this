package com.asciime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ImageSaver {
    public static File toFile(BufferedImage image) {
        return toFile(image, "/tmp/");
    }

    public static File toFile(BufferedImage image, String dir) {
        return toFile(image, dir, UUID.randomUUID().toString());
    }

    public static File toFile(BufferedImage image, String dir, String name) {
        File file = new File(dir + name + ".png");
        try {
            ImageIO.write(image, "png", file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
