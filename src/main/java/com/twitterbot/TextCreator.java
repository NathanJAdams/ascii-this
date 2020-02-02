package com.twitterbot;

import com.twitterbot.data.Person;
import com.twitterbot.data.SocialMedia;
import com.twitterbot.data.SocialMediaChanges;
import com.twitterbot.data.Theme;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TextCreator {
    public static String createText(Map<Person, SocialMediaChanges> peopleChanges, int max, SocialMedia socialMedia, Theme... themes) {
        StringBuilder sb = new StringBuilder();
        for (Theme theme : themes) {
            List<Map.Entry<Person, SocialMediaChanges>> sorted = new ArrayList<>(peopleChanges.entrySet());
            sorted.removeIf(entry -> !entry.getValue().getChanges().containsKey(socialMedia));
            sorted.removeIf(entry -> theme.getFunc().applyAsDouble(entry.getValue().getChanges().get(socialMedia)) < 0);
            sorted.sort(Comparator.comparing(entry -> entry.getKey().getName()));
            sorted.sort(Comparator.comparingDouble(entry -> -theme.getFunc().applyAsDouble(entry.getValue().getChanges().get(socialMedia))));
            while (sorted.size() > max) {
                sorted.remove(sorted.size() - 1);
            }
            max = Math.min(max, sorted.size());
            if (max > 0) {
                sb.append("Top ");
                sb.append(max);
                sb.append(' ');
                sb.append(theme.getLabel());
                sb.append(' ');
                sb.append(StatsGetter.TAG);
                sb.append(" on ");
                sb.append(socialMedia);
                sb.append('\n');
                for (int i = 0; i < sorted.size(); i++) {
                    Map.Entry<Person, SocialMediaChanges> entry = sorted.get(i);
                    sb.append(i + 1);
                    sb.append(". ");
                    Person person = entry.getKey();
                    sb.append("@");
                    sb.append(person.getAccounts().get(SocialMedia.Twitter));
                    sb.append("\n");
                }
                sb.append('\n');
            }
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
