package com.asciime;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ImageCreator {
    private static final NumberFormat FORMAT = DecimalFormat.getNumberInstance();
    private static final Stroke OUTLINE = new BasicStroke(1);

    private static final Color BACKGROUND = new Color(255, 255, 255);
    private static final Color BAR_OUTLINE = new Color(190, 190, 190);
    private static final Color BAR_BACKGROUND = new Color(240, 240, 240);
    private static final Color BAR_ACTUAL_NEGATIVE = new Color(255, 170, 170);
    private static final Color BAR_ACTUAL_POSITIVE = new Color(170, 170, 255);
    private static final Color TEXT = new Color(70, 70, 70);

    private static final Font TITLE_FONT = new Font("Arial", Font.PLAIN, 26);
    private static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 20);
    private static final Font FOOTNOTE_FONT = new Font("Arial", Font.PLAIN, 10);

    private static final int IMAGE_WIDTH = 460;
    private static final int IMAGE_HEIGHT = 512;

    private static final int TITLE_TOP = 35;
    private static final int ITEM_HEIGHT = 38;

    private static final int BAR_HEIGHT = 30;
    private static final int BAR_WIDTH = 420;
    private static final int BAR_LEFT = 20;
    private static final int BAR_TOP = 60;

    private static final int NAME_LEFT = BAR_LEFT + 8;
    private static final int NAME_TOP = BAR_TOP + 23;

    private static final int FOOTNOTE_TOP = 490;
    private static final int FOOTNOTE_HEIGHT = 15;

    static {
        FORMAT.setMaximumFractionDigits(2);
        FORMAT.setMinimumFractionDigits(2);
    }
}
