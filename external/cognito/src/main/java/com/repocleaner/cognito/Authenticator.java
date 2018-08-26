package com.repocleaner.cognito;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.repocleaner.util.RepoCleanerException;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class Authenticator {
    public DecodedJWT authenticate(String jwt) throws RepoCleanerException {
        DecodedJWT decoded = JWT.decode(jwt);
        Algorithm algorithm = createAlgorithm(decoded);
        JWTVerifier verifier = JWT.require(algorithm)
                .withAudience(CognitoInfo.CLIENT_ID)
                .acceptLeeway(100)
                .build();
        try {
            return verifier.verify(jwt);
        } catch (JWTVerificationException e) {
            throw new RepoCleanerException("Not authenticated", e);
        }
    }

    private Algorithm createAlgorithm(DecodedJWT decoded) throws RepoCleanerException {
        String keyId = decoded.getKeyId();
        PublicKey key = CognitoInfo.getKey(keyId);
        BigInteger modulus = toBigInt(key.getN());
        BigInteger exponent = toBigInt(key.getE());

        try {
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            return Algorithm.RSA256(publicKey, null);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RepoCleanerException("Not authenticated", e);
        }
    }

    private BigInteger toBigInt(String base64String) {
        byte[] decoded = Base64.getUrlDecoder().decode(base64String);
        return new BigInteger(1, decoded);
    }
}
