package com.asciime;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ImageCreator {
    private static final Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, CharacterSize.CHAR_HEIGHT);

    public static BufferedImage toImage(char c) {
        int width = CharacterSize.CHAR_WIDTH;
        int height = CharacterSize.CHAR_HEIGHT;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.setFont(FONT);
        g.drawString(String.valueOf(c), 0, height - 2);
        return image;
    }

    public static BufferedImage toImage(List<String> lines) {
        int width = lines.get(0).length() * CharacterSize.CHAR_WIDTH;
        int height = lines.size() * CharacterSize.CHAR_HEIGHT;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        int top = CharacterSize.CHAR_HEIGHT - 2;
        g.setFont(FONT);
        for (String line : lines) {
            g.drawString(line, 0, top);
            top += CharacterSize.CHAR_HEIGHT;
        }
        return image;
    }
}
