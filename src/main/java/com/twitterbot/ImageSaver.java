package com.twitterbot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ImageSaver {
    public static File toFile(BufferedImage image) {
        String name = UUID.randomUUID().toString();
        File file = new File("/tmp/" + name + ".png");
        try {
            ImageIO.write(image, "png", file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
