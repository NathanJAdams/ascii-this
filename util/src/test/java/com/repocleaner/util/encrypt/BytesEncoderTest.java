package com.repocleaner.util.encrypt;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class BytesEncoderTest {
    @Test
    public void testEncoded() {
        Random random = new Random();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        String encoded = BytesEncoder.encode(bytes);
        byte[] decoded = BytesEncoder.decode(encoded);
        Assert.assertArrayEquals(bytes, decoded);
    }
}
