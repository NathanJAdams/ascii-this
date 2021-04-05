package com.asciime;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class AsciiConverter {
    public static BufferedImage convert(BufferedImage image) {
        int width = image.getWidth();
        int charsWide = width / CharacterSize.CHAR_WIDTH;
        int charsHigh = image.getHeight() / CharacterSize.CHAR_HEIGHT;
        int[] grayscale = Grayscale.toGrayscale(image, true);
        List<String> lines = new ArrayList<>();
        for (int y = 0; y < charsHigh; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < charsWide; x++) {
                char c = AsciiPixels.closest(width, grayscale, x * CharacterSize.CHAR_WIDTH, y * CharacterSize.CHAR_HEIGHT);
                sb.append(c);
            }
            lines.add(sb.toString());
        }
        return ImageCreator.toImage(lines);
    }
}
