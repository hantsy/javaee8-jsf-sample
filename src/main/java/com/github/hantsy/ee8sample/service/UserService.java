/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.hantsy.ee8sample.service;

import com.github.hantsy.ee8sample.domain.LoginToken;
import com.github.hantsy.ee8sample.domain.User;
import com.github.hantsy.ee8sample.repository.LoginTokenRepository;
import com.github.hantsy.ee8sample.repository.UserRepository;
import java.time.LocalDateTime;
import static java.util.UUID.randomUUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import static org.omnifaces.utils.security.MessageDigests.digest;

/**
 *
 * @author Hantsy <hantsy@gmail.com>
 */
@ApplicationScoped
public class UserService {
    
    private static final String MESSAGE_DIGEST_ALGORITHM = "SHA-256";
    
    @Inject
    private LoginTokenRepository loginTokens;

    @Inject
    private UserRepository users;

    public String generate(String email, String ipAddress, String description, LoginToken.TokenType tokenType) {
        LocalDateTime expiration = LocalDateTime.now().plusDays(14);
        return generate(email, ipAddress, description, tokenType, expiration);
    }

    public String generate(String username, String ipAddress, String description, LoginToken.TokenType tokenType, LocalDateTime expiration) {
        String rawToken = randomUUID().toString();
        User user = users.findByUsername(username).orElseThrow(()-> new InvalidUsernameException(username));

        LoginToken loginToken = new LoginToken();
        loginToken.setTokenHash(digest(rawToken, MESSAGE_DIGEST_ALGORITHM));
        loginToken.setExpiredDate(expiration);
        loginToken.setDescription(description);
        loginToken.setType(tokenType);
        loginToken.setIpAddress(ipAddress);
        loginToken.setUser(user);
        user.getLoginTokens().add(loginToken);
        users.save(user);
        return rawToken;
    }

    public void remove(String loginToken) {
         this.loginTokens.removeByTokenHash(digest(loginToken, MESSAGE_DIGEST_ALGORITHM));
    }

}
