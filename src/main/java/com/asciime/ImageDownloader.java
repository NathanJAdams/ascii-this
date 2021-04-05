package com.asciime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageDownloader {
    public static BufferedImage toImage(String urlString) {
        try {
            URL url = new URL(urlString);
            InputStream is = url.openStream();
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
