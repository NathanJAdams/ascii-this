package com.repocleaner.userinfo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import sun.security.rsa.RSAPublicKeyImpl;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class Authenticator {
    public DecodedJWT authenticate(String jwt) {
        DecodedJWT decoded = JWT.decode(jwt);
        Algorithm algorithm = createAlgorithm(decoded);
        if (algorithm == null) {
            return null;
        }
        JWTVerifier verifier = JWT.require(algorithm)
                .withAudience(AppInfo.CLIENT_ID)
                .acceptLeeway(100)
                .build();
        try {
            return verifier.verify(jwt);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Algorithm createAlgorithm(DecodedJWT decoded) {
        String keyId = decoded.getKeyId();
        PublicKey key = AppInfo.getKey(keyId);
        if (key == null) {
            return null;
        }
        BigInteger modulus = toBigInt(key.getN());
        BigInteger exponent = toBigInt(key.getE());

        try {
            RSAPublicKey rsaPublicKey = new RSAPublicKeyImpl(modulus, exponent);
            return Algorithm.RSA256(rsaPublicKey, null);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    private BigInteger toBigInt(String base64String) {
        byte[] decoded = Base64.getUrlDecoder().decode(base64String);
        return new BigInteger(1, decoded);
    }
}
