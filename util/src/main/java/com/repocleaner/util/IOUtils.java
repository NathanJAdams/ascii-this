package com.repocleaner.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class IOUtils {
    public static void saveToFile(String string, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             OutputStreamWriter writer = new OutputStreamWriter(bos)) {
            writer.write(string);
        }
    }

    public static String toString(File file, Charset charset) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            return toString(bis, charset);
        }
    }

    public static String toString(InputStream is, Charset charset) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        byte[] bytes = buffer.toByteArray();
        return new String(bytes, charset);
    }

    public static long copy(File input, File output) throws IOException {
        try (FileInputStream fis = new FileInputStream(input)) {
            return copy(fis, output);
        }
    }

    public static long copy(InputStream is, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            return copy(is, bos);
        }
    }

    public static long copy(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[1024];
        long total = 0;
        while (true) {
            int bytesRead = is.read(buffer);
            if (bytesRead == -1) {
                break;
            }
            os.write(buffer, 0, bytesRead);
            total += bytesRead;
        }
        return total;
    }
}
