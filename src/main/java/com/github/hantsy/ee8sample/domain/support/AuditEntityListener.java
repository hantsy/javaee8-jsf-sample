/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.hantsy.ee8sample.domain.support;

import com.github.hantsy.ee8sample.security.UserInfo;
import com.github.hantsy.ee8sample.security.Authenticated;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 *
 * @author hantsy
 */
public class AuditEntityListener {

    private static final Logger LOG = Logger.getLogger(AuditEntityListener.class.getName());

    @PrePersist
    public void beforePersist(Object entity) {
        if (entity instanceof AbstractAuditableEntity) {
            AbstractAuditableEntity o = (AbstractAuditableEntity) entity;
            final LocalDateTime now = LocalDateTime.now();
            o.setCreatedDate(now);
            o.setLastModifiedDate(now);

            if (o.getCreatedBy() == null) {
                o.setCreatedBy(currentUser());
            }
        }
    }

    @PreUpdate
    public void beforeUpdate(Object entity) {
        if (entity instanceof AbstractAuditableEntity) {
            AbstractAuditableEntity o = (AbstractAuditableEntity) entity;
            o.setLastModifiedDate(LocalDateTime.now());

            if (o.getLastModifiedBy()== null) {
                o.setLastModifiedBy(currentUser());
            }
        }
    }

    private String currentUser() {
        UserInfo user = CDI.current().select(UserInfo.class, Authenticated.INSTANCE).get();
        LOG.log(Level.FINEST, "get current user form EntityListener@{0}", user);
        if (user == null) {
            return null;
        }
        return user.getName();
    }
}

