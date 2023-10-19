package com.hoaxify.ws.auth.token;

import com.hoaxify.ws.auth.dto.Credentials;
import com.hoaxify.ws.user.User;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class BasicAuthService implements TokenService {
    @Override
    public Token createToken(User user, Credentials creds) {

        String emailColonPassword = creds.email() + ":" + creds.password();
        String token = Base64.getEncoder().encodeToString(emailColonPassword.getBytes());
        return new Token("Basic", token);
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        return null;
    }
}
