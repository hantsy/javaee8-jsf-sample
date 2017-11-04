/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.hantsy.ee8sample.repository;

import com.github.hantsy.ee8sample.domain.LoginToken;
import com.github.hantsy.ee8sample.domain.User;
import com.github.hantsy.ee8sample.domain.support.AbstractRepository;
import java.util.Objects;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Hantsy <hantsy@gmail.com>
 */
@Stateless
public class UserRepository extends AbstractRepository<User, Long> {

    @PersistenceContext
    EntityManager em;

    public Optional<User> findByUsername(String username) {
        Objects.requireNonNull(username);
        return this.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    protected EntityManager entityManager() {
        return em;
    }

    public Optional<User> findByLoginToken(String token, LoginToken.TokenType tokenType) {
        return this.em.createNativeQuery(User.QUERY_BY_LOGIN_TOKEN, User.class)
                .setParameter("tokenHash", token)
                .setParameter("tokenType", tokenType)
                .getResultStream()
                .findFirst();
    }

}
