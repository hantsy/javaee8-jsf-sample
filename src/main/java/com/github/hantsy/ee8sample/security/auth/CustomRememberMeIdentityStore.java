package com.github.hantsy.ee8sample.security.auth;

import static com.github.hantsy.ee8sample.domain.LoginToken.TokenType.REMEMBER_ME;
import com.github.hantsy.ee8sample.repository.UserRepository;
import com.github.hantsy.ee8sample.service.UserService;
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import javax.servlet.http.HttpServletRequest;


@ApplicationScoped
public class CustomRememberMeIdentityStore implements RememberMeIdentityStore {

    @Inject
    private HttpServletRequest request;
    
    @Inject
    private UserRepository users;

    @Inject
    private UserService userService;

    @Override
    public CredentialValidationResult validate(RememberMeCredential credential) {
        return users.findByLoginToken(credential.getToken(), REMEMBER_ME)
                .map(u -> new CredentialValidationResult(new CallerPrincipal(u.getUsername()), u.getRoles()))
                .orElse(INVALID_RESULT);
    }

    @Override
    public String generateLoginToken(CallerPrincipal callerPrincipal, Set<String> groups) {
        return userService.generate(callerPrincipal.getName(), request.getRemoteAddr(), getDescription(), REMEMBER_ME);
    }

    @Override
    public void removeLoginToken(String loginToken) {
        userService.remove(loginToken);
    }

    private String getDescription() {
        return "Remember me session: " + request.getHeader("User-Agent");
    }

}
