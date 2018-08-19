package com.repocleaner.userinfo.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@AllArgsConstructor
@Getter
@ToString
public class PublicKey {
    private final String alg;
    private final String e;
    private final String kid;
    private final String kty;
    private final String n;
    private final String use;

    public boolean hasKeyId(String keyId) {
        return Objects.equals(kid, keyId);
    }
}
