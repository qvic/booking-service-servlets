package com.epam.bookingservice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PasswordEncryptorTest {

    private static final String TEST_SALT = "salt";

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"pepper", "67eb0e8861dd912c0ad27739eda48cb0f0d017fc4b414239de8917cabaa7c7e6f09595e0db055c2fe41abd43d59df0ae86cbd9ab241003845bdd46f900ad8757"},
                {"pass", "4ab3490b9dd9fbcd6eb9ec6e2078a99c8de5d4d0ae1371faad97fdc83774dbeeec52c971c41971f71b131587c1becb1707435b24771d392631298647ba04e37d"}
        });
    }

    private PasswordEncryptor passwordEncryptor;
    private String password;
    private String expectedHash;

    public PasswordEncryptorTest(String password, String expectedHash) {
        this.password = password;
        this.expectedHash = expectedHash;
        this.passwordEncryptor = new PasswordEncryptor();
    }

    @Test
    public void testEncrypt() {
        String passwordHash = passwordEncryptor.encrypt(password, TEST_SALT);

        assertEquals(expectedHash, passwordHash);
    }
}