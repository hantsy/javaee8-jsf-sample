/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.hantsy.ee8sample.repository;

import com.github.hantsy.ee8sample.domain.LoginToken;
import com.github.hantsy.ee8sample.domain.support.AbstractRepository;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Hantsy <hantsy@gmail.com>
 */
@Stateless
public class LoginTokenRepository extends AbstractRepository<LoginToken, Long> {

    @PersistenceContext
    EntityManager em;

    @Override
    protected EntityManager entityManager() {
        return em;
    }

    public void removeByTokenHash(byte[] tokenHash) {
        this.em.createNamedQuery("LoginToken.remove")
                .setParameter("tokenHash", tokenHash)
                .executeUpdate();
    }

}
