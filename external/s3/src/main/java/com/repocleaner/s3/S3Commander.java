package com.repocleaner.s3;

import com.repocleaner.util.HystrixCommander;
import com.repocleaner.util.Constants;

import java.io.File;

public class S3Commander {
    public static boolean download(String bucket, String key, File file) {
        S3DownloadCommand command = new S3DownloadCommand(bucket, key, file);
        return HystrixCommander.run(Constants.HYSTRIX_GROUP_S3_DOWNLOAD, command::download);
    }

    public static boolean upload(String bucket, String key, File file) {
        S3UploadCommand command = new S3UploadCommand(bucket, key, file);
        return HystrixCommander.run(Constants.HYSTRIX_GROUP_S3_UPLOAD, command::upload);
    }

    public static boolean delete(String bucket, String key) {
        S3DeleteCommand command = new S3DeleteCommand(bucket, key);
        return HystrixCommander.run(Constants.HYSTRIX_GROUP_S3_DELETE, command::delete);
    }
}
