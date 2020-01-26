package com.epam.app.service;

import com.epam.app.service.exception.NoSuchAlgorithmRuntimeException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {

    private static final int HASH_RADIX = 16;

    private String getSHA512(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger bigInteger = new BigInteger(1, messageDigest);
        return bigInteger.toString(HASH_RADIX);
    }

    public String encrypt(String password, String salt) {
        try {
            return getSHA512(salt + password);
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmRuntimeException(e);
        }
    }
}
