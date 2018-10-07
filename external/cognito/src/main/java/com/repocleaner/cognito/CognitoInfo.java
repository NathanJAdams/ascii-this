package com.repocleaner.cognito;

import com.google.gson.Gson;
import com.repocleaner.util.Constants;
import com.repocleaner.util.IOUtils;
import com.repocleaner.util.RepoCleanerException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class CognitoInfo {
    private static final Gson GSON = new Gson();
    private static final PublicKeys PUBLIC_KEYS;

    static {
        PublicKeys publicKeys = null;
        try {
            publicKeys = getPublicKeys();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PUBLIC_KEYS = publicKeys;
    }

    public static PublicKey getKey(String keyId) throws RepoCleanerException {
        if ((PUBLIC_KEYS == null) || !PUBLIC_KEYS.hasKeys()) {
            throw new RepoCleanerException("No public key available");
        }
        PublicKey publicKey = PUBLIC_KEYS.getKey(keyId);
        if (publicKey == null) {
            throw new RepoCleanerException("No public key available");
        }
        return publicKey;
    }

    public static PublicKeys getPublicKeys() throws IOException {
        URL url = new URL(Constants.COGNITO_PUBLIC_KEYS_ADDRESS);
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        String response = IOUtils.toString(is, StandardCharsets.UTF_8);
        return GSON.fromJson(response, PublicKeys.class);
    }
}
