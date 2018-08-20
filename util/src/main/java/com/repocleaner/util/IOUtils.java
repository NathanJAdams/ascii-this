package com.repocleaner.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class IOUtils {
    public static String toString(InputStream is, Charset charset) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] bytes = buffer.toByteArray();
        return new String(bytes, charset);
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
