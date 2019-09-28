package com.twitterbot;

import com.twitterbot.data.Person;
import com.twitterbot.data.SocialMedia;
import com.twitterbot.data.SocialMediaChanges;
import com.twitterbot.data.StatsRange;
import com.twitterbot.data.Theme;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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

    public static BufferedImage createImage(SocialMedia socialMedia, Map<Person, SocialMediaChanges> peopleChanges, Theme theme, int max, long today, int days) {
        System.out.println("Creating image for " + socialMedia + " with theme " + theme);
        BufferedImage bufferedImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

        int barTop = BAR_TOP;
        int nameTop = NAME_TOP;
        List<Map.Entry<Person, SocialMediaChanges>> sorted = new ArrayList<>(peopleChanges.entrySet());
        sorted.removeIf(entry -> !entry.getValue().getChanges().containsKey(socialMedia));
        sorted.removeIf(entry -> theme.getFunc().applyAsDouble(entry.getValue().getChanges().get(socialMedia)) < 0);
        sorted.sort(Comparator.comparing(entry -> entry.getKey().getName()));
        sorted.sort(Comparator.comparingDouble(entry -> -theme.getFunc().applyAsDouble(entry.getValue().getChanges().get(socialMedia))));
        while (sorted.size() > max) {
            sorted.remove(sorted.size() - 1);
        }
        max = sorted.size();
        if (max <= 0) {
            System.out.println("There are only " + max + " entries for " + socialMedia + " so won't create an image");
            return null;
        }
        g.setFont(TITLE_FONT);
        g.setColor(TEXT);
        g.drawString("Top " + max + " Viral " + socialMedia + " Users:", BAR_LEFT, TITLE_TOP);
        g.drawString(StatsGetter.TAG, BAR_LEFT, TITLE_TOP + 40);

        StatsRange statsRange = theme.getStatsRange(socialMedia, sorted);
        double range = statsRange.getMax() - statsRange.getMin();
        int zero = 0;
        if (statsRange.getMin() < 0) {
            zero = (int) Math.round((-statsRange.getMin() / range) * BAR_WIDTH);
        }
        System.out.println(statsRange);
        g.setFont(TEXT_FONT);
        for (Map.Entry<Person, SocialMediaChanges> entry : sorted) {
            double change = theme.getFunc().applyAsDouble(entry.getValue().getChanges().get(socialMedia));
            int actualWidth = (int) Math.round(Math.abs(change) * BAR_WIDTH / range);
            barTop += ITEM_HEIGHT;
            int barLeft;
            nameTop += ITEM_HEIGHT;
            g.setColor(BAR_BACKGROUND);
            g.fillRoundRect(BAR_LEFT, barTop, BAR_WIDTH, BAR_HEIGHT, 2, 2);
            if (change < 0) {
                barLeft = BAR_LEFT + zero - actualWidth;
                g.setColor(BAR_ACTUAL_NEGATIVE);
            } else {
                barLeft = BAR_LEFT + Math.max(0, zero);
                g.setColor(BAR_ACTUAL_POSITIVE);
            }
            g.fillRoundRect(barLeft, barTop, actualWidth, BAR_HEIGHT, 2, 2);
            g.setColor(BAR_OUTLINE);
            g.setStroke(OUTLINE);
            g.drawRoundRect(BAR_LEFT, barTop, BAR_WIDTH, BAR_HEIGHT, 2, 2);
            g.setColor(TEXT);
            if (zero != 0) {
                g.setColor(BAR_OUTLINE);
                g.drawLine(BAR_LEFT + zero, barTop, BAR_LEFT + zero, barTop + BAR_HEIGHT);
            }
            g.setColor(TEXT);
            Person person = entry.getKey();
            String text = person.getName() + " (" + person.getAffiliation() + ") +" + theme.getFormat().format(change);
            g.drawString(text, NAME_LEFT, nameTop);
        }
        LocalDate from = LocalDate.ofEpochDay(today - days);
        LocalDate to = LocalDate.ofEpochDay(today);
        g.setFont(FOOTNOTE_FONT);
        g.drawString("Percentage increase in daily " + socialMedia.getAccountName(), BAR_LEFT, FOOTNOTE_TOP);
        g.drawString("(averaged between " + from + " 8:00am CST and " + to + " 8:00am CST)", BAR_LEFT, FOOTNOTE_TOP + FOOTNOTE_HEIGHT);
        return bufferedImage;
    }
}
