package com.repocleaner.util;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ZipUtilTest {
    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testZipFile() throws IOException, RepoCleanerException {
        File root = temporaryFolder.newFolder("root");
        File zipped = new File(temporaryFolder.getRoot(), "zipped");
        File unzipped = new File(temporaryFolder.getRoot(), "unzipped");

        String fileName = "file.txt";
        String message = "Hello";

        File file = new File(root, fileName);
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             OutputStreamWriter writer = new OutputStreamWriter(bos)) {
            writer.write(message);
        }

        ZipUtil.zip(root, zipped);
        ZipUtil.extract(zipped, unzipped);

        File output = new File(unzipped, fileName);
        try (FileInputStream fis = new FileInputStream(output);
             BufferedInputStream bis = new BufferedInputStream(fis);
             InputStreamReader reader = new InputStreamReader(bis)) {
            int length = message.length();
            char[] buffer = new char[length];
            int count = reader.read(buffer, 0, length);
            Assert.assertEquals(length, count);
            Assert.assertArrayEquals(message.toCharArray(), buffer);
        }
    }
}
