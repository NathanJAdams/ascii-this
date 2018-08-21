package com.repocleaner.s3;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.repocleaner.util.RepoCleanerException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class S3Info {
    public static final String PREPARED_BUCKET = "prepared.repocleaner.com";
    public static final String CLEANED_BUCKET = "cleaned.repocleaner.com";

    public static final AmazonS3 CLIENT = AmazonS3ClientBuilder.standard()
            .withCredentials(new ProfileCredentialsProvider())
            .withRegion("eu-west-1")
            .build();

    public static String getDecodedKey(String key) throws RepoCleanerException {
        key = key.replace('+', ' ');
        try {
            return URLDecoder.decode(key, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RepoCleanerException("Failed to decode key", e);
        }
    }
}
