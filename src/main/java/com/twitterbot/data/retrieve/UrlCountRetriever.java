package com.twitterbot.data.retrieve;

import com.twitterbot.data.CountRetriever;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class UrlCountRetriever implements CountRetriever {
    private final UrlCreator urlCreator;
    private final ConnectionModifier connectionModifier;
    private final CountExtractor countExtractor;

    @Override
    public int retrieveCount(String account) {
        String urlString = urlCreator.createUrl(account);
        System.out.println(urlString);
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connectionModifier != null) {
                connectionModifier.modify(connection, account);
            }
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            for (String responseLine = br.readLine(); responseLine != null; responseLine = br.readLine()) {
                sb.append(responseLine);
            }
            String content = sb.toString();
            System.out.println(content);
            int count = countExtractor.extract(content);
            System.out.println(count);
            return count;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
