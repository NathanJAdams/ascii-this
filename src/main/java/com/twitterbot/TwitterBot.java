package com.twitterbot;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.google.gson.Gson;
import com.twitterbot.data.Change;
import com.twitterbot.data.People;
import com.twitterbot.data.Person;
import com.twitterbot.data.SocialMedia;
import com.twitterbot.data.SocialMediaChanges;
import com.twitterbot.data.Theme;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwitterBot implements RequestHandler<S3Event, Void> {
    private static final int DAYS_AVERAGED = 7;
    private static final Gson GSON = new Gson();

    public static void main(String[] args) {
        new TwitterBot().handleRequest(null, null);
    }

    public Void handleRequest(S3Event event, Context context) {
        People people = getPeople();
        if (people == null) {
            System.out.println("No people found");
            return null;
        }
        long today = LocalDate.now().toEpochDay();
        Map<Person, SocialMediaChanges> peopleChanges = StatsGetter.getPeopleChanges(people, today, DAYS_AVERAGED);
        if (peopleChanges != null && !peopleChanges.isEmpty()) {
            int max = 10;
            List<Long> mediaIDsList = new ArrayList<>();
            for (SocialMedia socialMedia : SocialMedia.values()) {
                for (Theme theme : Theme.values()) {
                    BufferedImage image = ImageCreator.createImage(socialMedia, peopleChanges, theme, max, today, DAYS_AVERAGED);
                    File file = ImageSaver.toFile(image);
                    mediaIDsList.add(TwitterAPI.uploadFile(file));
                }
            }
            long[] mediaIDs = new long[mediaIDsList.size()];
            for (int i = 0; i < mediaIDsList.size(); i++) {
                mediaIDs[i] = mediaIDsList.get(i);
            }
            String text = TextCreator.createText(peopleChanges, Theme.Percentage, 3);
            TwitterAPI.tweet(text, mediaIDs);
        }
        return null;
    }

    private People getPeople() {
        try (InputStream is = TwitterBot.class.getResourceAsStream("/people.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            return GSON.fromJson(isr, People.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<Person, SocialMediaChanges> getChanges() {
        Map<Person, SocialMediaChanges> changes = new HashMap<>();
        changes.put(getPerson("Afnjdksn FKfdsfds", "REP"), new SocialMediaChanges(getSocialMediaChanges()));
        changes.put(getPerson("Bfdsfds Gfjdskfhdso", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
        changes.put(getPerson("Cfpoei Dfdfds", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
        changes.put(getPerson("Efds Ifnksfhkjds", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
        changes.put(getPerson("Flgfsdg Ffdsfds", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
        changes.put(getPerson("Gfds Gfjdsaf", "REP"), new SocialMediaChanges(getSocialMediaChanges()));
        changes.put(getPerson("Hfds Hfds", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
        changes.put(getPerson("Iureuwiew Imcvds", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
        changes.put(getPerson("Jfds Jfds", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
        changes.put(getPerson("Kfds Kfdsdfdsgfd", "IND"), new SocialMediaChanges(getSocialMediaChanges()));
        changes.put(getPerson("Lmfds Lfdsa", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
        changes.put(getPerson("Mfosiew Masfdc", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
        return changes;
    }

    private Person getPerson(String name, String affiliation) {
        Map<SocialMedia, String> accounts = new HashMap<>();
        accounts.put(SocialMedia.Twitter, name.replace(" ", "_"));
        accounts.put(SocialMedia.Facebook, name.replace(" ", "_"));
        return new Person(name, affiliation, accounts);
    }

    private Map<SocialMedia, Change> getSocialMediaChanges() {
        Map<SocialMedia, Change> changes = new HashMap<>();
        changes.put(SocialMedia.Twitter, new Change(Math.random(), 1000 * Math.random()));
        changes.put(SocialMedia.Facebook, new Change(Math.random(), 1000 * Math.random()));
        return changes;
    }
}
