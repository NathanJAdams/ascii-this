package com.repocleaner.userinfo.security;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PublicKeys {
    private final List<PublicKey> keys;

    public boolean hasKeys() {
        return (keys != null)
                && !keys.isEmpty();
    }

    public PublicKey getKey(String keyId) {
        return keys.stream()
                .filter(key -> key.hasKeyId(keyId))
                .findFirst()
                .orElse(null);
    }
}
