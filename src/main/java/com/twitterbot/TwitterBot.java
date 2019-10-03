package com.twitterbot;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.google.gson.Gson;
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
            tweet(peopleChanges, today, SocialMedia.Twitter);
            tweet(peopleChanges, today, SocialMedia.Facebook);
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

    private void tweet(Map<Person, SocialMediaChanges> peopleChanges, long today, SocialMedia socialMedia) {
        Map<Theme, Long> themeIDs = new HashMap<>();
        for (Theme theme : Theme.values()) {
            BufferedImage image = ImageCreator.createImage(socialMedia, peopleChanges, theme, 10, today, DAYS_AVERAGED);
            if (image != null) {
                File file = ImageSaver.toFile(image);
                long mediaId = TwitterAPI.uploadFile(file);
                themeIDs.put(theme, mediaId);
            }
        }
        if (!themeIDs.isEmpty()) {
            List<Long> mediaIDsList = new ArrayList<>(themeIDs.values());
            long[] mediaIDs = new long[mediaIDsList.size()];
            for (int i = 0; i < mediaIDsList.size(); i++) {
                mediaIDs[i] = mediaIDsList.get(i);
            }
            List<Theme> validThemes = new ArrayList<>();
            if (themeIDs.containsKey(Theme.Percentage))validThemes.add(Theme.Percentage);
            if (themeIDs.containsKey(Theme.Raw))validThemes.add(Theme.Raw);
            String text = TextCreator.createText(peopleChanges, 3, socialMedia, validThemes.toArray(new Theme[0]));
            System.out.println(text);
            TwitterAPI.tweet(text, mediaIDs);
        }
    }
//    private Map<Person, SocialMediaChanges> getPeopleChanges(){
//        People people = getPeople2();
//        DatedRawCounts early = getEarly();
//        DatedRawCounts late = getLate();
//        return StatsGetter.getPeopleChanges(people, early, late, 7);
//    }
//    private People getPeople2(){
//        List<Person>people=new ArrayList<>();
//        people.add(new Person("Aaa", "Q" ,createSocialMediaAccounts()));
//        people.add(new Person("Bbb", "Q" ,createSocialMediaAccounts()));
//        people.add(new Person("Ccc", "Q" ,createSocialMediaAccounts()));
//        people.add(new Person("Ddd", "Q" ,createSocialMediaAccounts()));
//        return new People(people);
//    }
//    private Map<SocialMedia,String> createSocialMediaAccounts(){
//        Map<SocialMedia,String>accounts = new HashMap<>();
//        accounts.put(SocialMedia.Twitter, "jfdkshgkdsf");
//        accounts.put(SocialMedia.Facebook, "jfdkshgkdsf");
//            accounts.put(SocialMedia.YouTube, "jfdkshgkdsf");
//            accounts.put(SocialMedia.Instagram, "jfdkshgkdsf");
//        return accounts;
//    }
//    private DatedRawCounts getEarly(){
//        return getDatedRawCounts(true);
//    }
//    private DatedRawCounts getLate(){
//        return getDatedRawCounts(false);
//    }
//private DatedRawCounts getDatedRawCounts(boolean early){
//        Map<String, SocialMediaCount>accountSocialMediaCounts = new HashMap<>();
//        accountSocialMediaCounts.put("Aaa", createSocialMediaCounts(early));
//        accountSocialMediaCounts.put("Bbb", createSocialMediaCounts(early));
//        accountSocialMediaCounts.put("Ccc", createSocialMediaCounts(early));
//        accountSocialMediaCounts.put("Ddd", createSocialMediaCounts(early));
//        int day =early? 100:107;
//        return new DatedRawCounts("TAG", day, accountSocialMediaCounts);
//}
//private SocialMediaCount createSocialMediaCounts(boolean early){
//        Map<SocialMedia, Integer>socialMediaCounts = new HashMap<>();
//        socialMediaCounts.put(SocialMedia.Twitter, 100+ (int)(Math.random()*100));
//        socialMediaCounts.put(SocialMedia.Facebook, 100+ (int)(Math.random()*100));
//    socialMediaCounts.put(SocialMedia.YouTube, 100+ (int)(Math.random()*100));
//    socialMediaCounts.put(SocialMedia.Instagram, 100+ (int)(Math.random()*100));
//    if(!early){
//            for (Map.Entry<SocialMedia, Integer>entry:socialMediaCounts.entrySet()) {
//                entry.setValue(entry.getValue()+10);
//            }
//        }
//        return new SocialMediaCount(socialMediaCounts);
//}
//    private Map<Person, SocialMediaChanges> getChanges() {
//        Map<Person, SocialMediaChanges> changes = new HashMap<>();
//        changes.put(getPerson("Afnjdksn FKfdsfds", "REP"), new SocialMediaChanges(getSocialMediaChanges()));
//        changes.put(getPerson("Bfdsfds Gfjdskfhdso", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
//        changes.put(getPerson("Cfpoei Dfdfds", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
//        changes.put(getPerson("Efds Ifnksfhkjds", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
//        changes.put(getPerson("Flgfsdg Ffdsfds", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
//        changes.put(getPerson("Gfds Gfjdsaf", "REP"), new SocialMediaChanges(getSocialMediaChanges()));
//        changes.put(getPerson("Hfds Hfds", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
//        changes.put(getPerson("Iureuwiew Imcvds", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
//        changes.put(getPerson("Jfds Jfds", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
//        changes.put(getPerson("Kfds Kfdsdfdsgfd", "IND"), new SocialMediaChanges(getSocialMediaChanges()));
//        changes.put(getPerson("Lmfds Lfdsa", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
//        changes.put(getPerson("Mfosiew Masfdc", "DEM"), new SocialMediaChanges(getSocialMediaChanges()));
//        return changes;
//    }
//    private Person getPerson(String name, String affiliation) {
//        Map<SocialMedia, String> accounts = new HashMap<>();
//        accounts.put(SocialMedia.Twitter, name.replace(" ", "_"));
//        accounts.put(SocialMedia.Facebook, name.replace(" ", "_"));
//        accounts.put(SocialMedia.Instagram, name.replace(" ", "_"));
//        accounts.put(SocialMedia.YouTube, name.replace(" ", "_"));
//        return new Person(name, affiliation, accounts);
//    }
//    private Map<SocialMedia, Change> getSocialMediaChanges() {
//        Map<SocialMedia, Change> changes = new HashMap<>();
//        changes.put(SocialMedia.Twitter, new Change(Math.random(), 1000 * Math.random()));
//        changes.put(SocialMedia.Facebook, new Change(Math.random(), 1000 * Math.random()));
//        changes.put(SocialMedia.Instagram, new Change(Math.random(), 1000 * Math.random()));
//        changes.put(SocialMedia.YouTube, new Change(Math.random(), 1000 * Math.random()));
//        return changes;
//    }
}
