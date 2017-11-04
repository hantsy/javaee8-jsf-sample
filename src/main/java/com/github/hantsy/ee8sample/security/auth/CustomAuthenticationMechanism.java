/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.hantsy.ee8sample.security.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hantsy <hantsy@gmail.com>
 */
@AutoApplySession // For "Is user already logged-in?"
@RememberMe(isRememberMeExpression = "httpMessageContext.authParameters.rememberMe", cookieMaxAgeSeconds = 60 * 60 * 24 * 14) // 14 days
@LoginToContinue(loginPage = "/login.xhtml?continue=true", errorPage = "", useForwardToLogin = false)
@ApplicationScoped
public class CustomAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    private IdentityStore identityStore;

    @Override
    public AuthenticationStatus validateRequest(
            HttpServletRequest request, 
            HttpServletResponse response, 
            HttpMessageContext context) throws AuthenticationException {
        
        Credential credential = context.getAuthParameters().getCredential();

        if (credential != null) {
            return context.notifyContainerAboutLogin(identityStore.validate(credential));
        } else {
            return context.doNothing();
        }
    }

    // Workaround for CDI bug; default methods are not intercepted.
    // Fixed in Weld 2.4.0 and 3.0.0, which we'll eventually target.
    // See https://issues.jboss.org/browse/WELD-2093
//    @Override
//    public void cleanSubject(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
//        HttpAuthenticationMechanism.super.cleanSubject(request, response, httpMessageContext);
//    }

}
