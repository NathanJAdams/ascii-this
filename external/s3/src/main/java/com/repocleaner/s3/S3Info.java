package com.repocleaner.s3;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.repocleaner.util.Constants;

import java.io.File;

public class S3Info {
    public static final File TMP_FOLDER = new File("/tmp");
    public static final AmazonS3 CLIENT = AmazonS3ClientBuilder.standard()
            .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
            .withRegion(Constants.AWS_REGION)
            .build();
}
