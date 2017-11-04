/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.hantsy.ee8sample.security.auth;

import com.github.hantsy.ee8sample.repository.UserRepository;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.PasswordHash;

/**
 *
 * @author Hantsy <hantsy@gmail.com>
 */
@ApplicationScoped
public class CustomIdentityStore implements IdentityStore {

    @Inject
    UserRepository users;

    @Inject
    PasswordHash passwordHash;

    @Override
    public CredentialValidationResult validate(Credential credential) {

        if (credential instanceof UsernamePasswordCredential) {
            String username = ((UsernamePasswordCredential) credential).getCaller();
            String password = ((UsernamePasswordCredential) credential).getPasswordAsString();
            return users.findByUsername(username)
                    .map(u -> passwordHash.verify(password.toCharArray(), u.getPassword())
                    ? new CredentialValidationResult(u.getUsername(), u.getRoles())
                    : INVALID_RESULT)
                    .orElse(INVALID_RESULT);
        }

        if (credential instanceof CallerOnlyCredential) {
            String username = ((CallerOnlyCredential) credential).getCaller();
            return users.findByUsername(username)
                    .map(u -> new CredentialValidationResult(u.getUsername(), u.getRoles()))
                    .orElse(INVALID_RESULT);
        }

        return NOT_VALIDATED_RESULT;
    }

//    private static CredentialValidationResult validate(User user) {
//        if (!user.isActive()) {
//            throw new UserIsNotActiveException();
//        }
//
//        return new CredentialValidationResult(user.getUsername(), user.getRoles());
//    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        return validationResult.getCallerGroups();
    }
    

}
