package com.bookingservice.service.encoder;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    public String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verify(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
