package com.demcia.eleven.upms.domain;

import com.demcia.eleven.core.domain.AbstractDomain;
import com.demcia.eleven.core.security.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Table("upms_access_token")
@With
@Getter
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class AccessToken extends AbstractDomain<AccessToken> {

    @Id
    @Column("token_")
    private String token;

    @Column("issuer")
    private String issuer;

    @Column("create_at_")
    private LocalDateTime createAt;

    @Column("expire_at_")
    private LocalDateTime expireAt;

    @Column("principal_name")
    private String principalName;

    @Column("principal_type")
    private String principalType;

    @Column("client_ip_")
    private String clientIp;

    public AccessToken(Token token) {
        this.token = token.getValue();
        this.principalName = token.getPrincipal().getName();
        this.principalType = token.getPrincipal().getType();
        this.clientIp = token.getDetail().getClientIp();
        this.issuer = token.getIssuer();
        this.expireAt = token.getExpireAt();
        this.createAt = token.getCreateAt();
        super.markNew();
    }


    @Override
    public String getId() {
        return this.token;
    }
}