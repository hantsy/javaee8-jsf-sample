package com.github.hantsy.ee8sample.domain;

import com.github.hantsy.ee8sample.domain.support.AbstractEntity;
import static javax.persistence.EnumType.STRING;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@NamedQuery(
        name = "LoginToken.remove",
        query = "DELETE"
        + "			FROM"
        + "				LoginToken _loginToken"
        + "			WHERE"
        + "				_loginToken.tokenHash = :tokenHash"
)
@NamedQuery(
        name = "LoginToken.removeExpired",
        query = "DELETE"
        + "			FROM"
        + "				LoginToken _loginToken"
        + "			WHERE"
        + "				_loginToken.expiredDate < CURRENT_TIMESTAMP"
)
public class LoginToken extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    public enum TokenType {
        REMEMBER_ME,
        API,
        RESET_PASSWORD
    }

    @Column(unique = true)
    private String tokenHash;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime expiredDate;

    @Column(length = 45)
    private String ipAddress;

    @Column
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(STRING)
    private TokenType type;

    @PrePersist
    public void beforeSave() {
        createdDate = LocalDateTime.now();

        if (expiredDate == null) {
            expiredDate = LocalDateTime.now().plusMonths(1L);
        }
    }

}
