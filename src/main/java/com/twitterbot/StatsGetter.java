package com.twitterbot;

import com.google.gson.Gson;
import com.twitterbot.data.Change;
import com.twitterbot.data.DatedRawCounts;
import com.twitterbot.data.People;
import com.twitterbot.data.Person;
import com.twitterbot.data.SocialMedia;
import com.twitterbot.data.SocialMediaChanges;
import com.twitterbot.data.SocialMediaCount;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import twitter4j.DirectMessage;

public class StatsGetter {
    private static final String TAG = "#US Presidential Candidates";
    private static final Gson GSON = new Gson();

    public static Map<Person, SocialMediaChanges> getPeopleChanges(People people, int days) {
        long today = LocalDate.now().toEpochDay();
        long previous = today - days;
        DatedRawCounts previousDatedRawCounts = getPreviousDatedRawCounts(people, previous);
        DatedRawCounts todaysDatedRawCounts = getTodaysDatedRawCounts(people, today);
        sendDatedRawCountsAsDM(todaysDatedRawCounts);
        return (previousDatedRawCounts == null)
                ? null
                : getPeopleChanges(people, previousDatedRawCounts, todaysDatedRawCounts, days);
    }

    private static DatedRawCounts getPreviousDatedRawCounts(People people, long previous) {
        System.out.println("Getting previous raw counts");
        String epochDayString = String.valueOf(previous);
        List<DirectMessage> dms = TwitterAPI.getDMs(dm -> dm.getSenderId() == 1165949136967000064L
                && dm.getText().contains(TAG)
                && dm.getText().contains("\"epochDay\":" + epochDayString));
        if (dms.size() != 1) {
            System.out.println("Oh noes! Didn't find previous counts for " + epochDayString);
            return null;
        }
        String text = dms.get(0).getText();
        DatedRawCounts datedRawCounts = GSON.fromJson(text, DatedRawCounts.class);
        Set<String> accountNames = people.getPeople().stream().map(Person::getName).collect(Collectors.toSet());
        datedRawCounts.getAccountSocialMediaCounts().keySet().removeIf(name -> !accountNames.contains(name));
        return datedRawCounts;
    }

    private static DatedRawCounts getTodaysDatedRawCounts(People people, long today) {
        System.out.println("Getting todays raw counts");
        Map<String, SocialMediaCount> accountSocialMediaCounts = new HashMap<>();
        for (Person person : people.getPeople()) {
            Map<SocialMedia, Integer> socialMediaCounts = new HashMap<>();
            for (Map.Entry<SocialMedia, String> entry : person.getAccounts().entrySet()) {
                SocialMedia socialMedia = entry.getKey();
                String account = entry.getValue();
                int latest = socialMedia.getLatest(account);
                System.out.println(latest);
                socialMediaCounts.put(socialMedia, latest);
            }
            accountSocialMediaCounts.put(person.getName(), new SocialMediaCount(socialMediaCounts));
        }
        return new DatedRawCounts(TAG, today, accountSocialMediaCounts);
    }

    private static void sendDatedRawCountsAsDM(DatedRawCounts datedRawCounts) {
        System.out.println("Tweeting raw counts");
        System.out.println(datedRawCounts);
        TwitterAPI.dm("who_viral", GSON.toJson(datedRawCounts));
    }

    private static Map<Person, SocialMediaChanges> getPeopleChanges(People people, DatedRawCounts previousDatedRawCounts, DatedRawCounts todaysDatedRawCounts, int days) {
        System.out.println("Getting people changes");
        Map<Person, SocialMediaChanges> peopleChanges = new HashMap<>();
        for (Map.Entry<String, SocialMediaCount> entry : previousDatedRawCounts.getAccountSocialMediaCounts().entrySet()) {
            String name = entry.getKey();
            SocialMediaCount previousSocialMediaCount = entry.getValue();
            SocialMediaCount todaysSocialMediaCount = todaysDatedRawCounts.getAccountSocialMediaCounts().get(name);
            if (todaysSocialMediaCount != null) {
                Map<SocialMedia, Change> socialMediaChanges = new HashMap<>();
                for (Map.Entry<SocialMedia, Integer> socialMediaCount : previousSocialMediaCount.getSocialMediaCounts().entrySet()) {
                    SocialMedia socialMedia = socialMediaCount.getKey();
                    int count = socialMediaCount.getValue();
                    Integer todaysCount = todaysSocialMediaCount.getSocialMediaCounts().get(socialMedia);
                    if (todaysCount != null) {
                        Change change = calculateChange(count, todaysCount, days);
                        socialMediaChanges.put(socialMedia, change);
                    }
                }
                Person person = people.getPeople().stream().filter(p -> name.equals(p.getName())).findFirst().orElse(null);
                if (person == null) {
                    System.out.println("No person!");
                } else {
                    peopleChanges.put(person, new SocialMediaChanges(socialMediaChanges));
                }
            }
        }
        return peopleChanges;
    }

    private static Change calculateChange(int start, int end, int days) {
        double ratioChange;
        if (start == end || end == 0) {
            ratioChange = 0;
        } else {
            double multiple = (double) end / start;
            ratioChange = multiple - 1;
        }
        double rawChange = end - start;
        ratioChange /= days;
        rawChange /= days;
        return new Change(ratioChange, rawChange);
    }
}
