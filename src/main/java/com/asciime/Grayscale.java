package com.asciime;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Grayscale {
    private static final double RED_COMPONENT = 0.2126;
    private static final double GREEN_COMPONENT = 0.7152;
    private static final double BLUE_COMPONENT = 0.0722;

    public static int[] toGrayscale(BufferedImage image) {
        return toGrayscale(image, false);
    }

    public static int[] toGrayscale(BufferedImage image, boolean adjustGamma) {
        int width = image.getWidth();
        int height = image.getHeight();
        int pixels = width * height;
        double[] luminance = new double[pixels];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                int rgb = image.getRGB(x, y);

                int r = r(rgb);
                int g = g(rgb);
                int b = b(rgb);

                double rCorrected = r / 255.0;
                double gCorrected = g / 255.0;
                double bCorrected = b / 255.0;
                luminance[index] = RED_COMPONENT * rCorrected + GREEN_COMPONENT * gCorrected + BLUE_COMPONENT * bCorrected;
            }
        }
        if (adjustGamma) {
            double totalLuminance = Arrays.stream(luminance).sum();
            double meanLuminance = totalLuminance / pixels;
            double inverseGamma = Math.sqrt(meanLuminance);
            return Arrays.stream(luminance).mapToInt(p -> (int) (255.0 * Math.pow(p, inverseGamma))).toArray();
        } else {
            return Arrays.stream(luminance).mapToInt(p -> (int) (255.0 * p)).toArray();
        }
    }

    private static int r(int rgb) {
        return ((rgb >> 16) & 0xFF);
    }

    private static int g(int rgb) {
        return ((rgb >> 8) & 0xFF);
    }

    private static int b(int rgb) {
        return ((rgb >> 0) & 0xFF);
    }

    private static int rgb(int r, int g, int b) {
        return argb(255, r, g, b);
    }

    private static int argb(int a, int r, int g, int b) {
        return ((a & 0xFF) << 24)
                + ((r & 0xFF) << 16)
                + ((g & 0xFF) << 8)
                + ((b & 0xFF) << 0);
    }
}
