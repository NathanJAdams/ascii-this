package com.asciime;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class AsciiPixels {
    private static final Map<Character, int[]> CHAR_GRAYSCALES = new HashMap<>();

    static {
        for (char c = ' '; c <= '}'; c++) {
            addChar(c);
        }
    }

    private static void addChar(char c) {
        BufferedImage image = ImageCreator.toImage(c);
        int[] grayscale = Grayscale.toGrayscale(image);
        CHAR_GRAYSCALES.put(c, grayscale);
    }

    public static char closest(int imageGrayscaleWidth, int[] imageGrayscale, int x, int y) {
        char bestChar = ' ';
        long bestScore = Long.MAX_VALUE;
        for (Map.Entry<Character, int[]> entry : CHAR_GRAYSCALES.entrySet()) {
            char c = entry.getKey();
            int[] grayscale = entry.getValue();
            long absDiffs = 0;
            long sqDiffs = 0;
            for (int charY = 0; charY < CharacterSize.CHAR_HEIGHT; charY++) {
                for (int charX = 0; charX < CharacterSize.CHAR_WIDTH; charX++) {
                    int charIndex = CharacterSize.CHAR_WIDTH * charY + charX;
                    int charGray = grayscale[charIndex];
                    int imageIndex = imageGrayscaleWidth * (charY + y) + charX + x;
                    int imageGray = imageGrayscale[imageIndex];
                    long diff = charGray - imageGray;
                    absDiffs += diff;
                    sqDiffs += (diff * diff);
                }
            }

            long meanSqDiff = sqDiffs / CharacterSize.CHAR_PIXELS;
            long meanAbsDiff = absDiffs / CharacterSize.CHAR_PIXELS;
            long sqMeanDiff = meanAbsDiff * meanAbsDiff;
            long charScore = meanSqDiff + sqMeanDiff;
            if (charScore < bestScore) {
                bestScore = charScore;
                bestChar = c;
            }
        }
        return bestChar;
    }
}
