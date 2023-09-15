package com.eleven.upms.domain;

import com.eleven.core.domain.AbstractDomain;
import com.eleven.core.security.Principal;
import com.eleven.core.security.Token;
import com.eleven.core.security.TokenDetail;
import com.eleven.upms.model.AccessTokenCreatedEvent;
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
    @Column("token")
    private String token;

    @Column("issuer")
    private String issuer;

    @Column("create_at")
    private LocalDateTime createAt;

    @Column("expire_at")
    private LocalDateTime expireAt;

    @Column("principal_name")
    private String principalName;

    @Column("principal_type")
    private String principalType;

    @Column("client_ip")
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
        super.andEvent(new AccessTokenCreatedEvent(token.getValue()));
    }


    /**
     * 将令牌转换为服务器内部令牌对象
     *
     * @return 令牌对象
     */
    public Token toToken() {
        var accessToken = this;
        return new Token()
                .setIssuer(accessToken.getIssuer())
                .setValue(accessToken.getToken())
                .setExpireAt(accessToken.getExpireAt())
                .setCreateAt(accessToken.getCreateAt())
                .setPrincipal(new Principal(accessToken.getPrincipalType(), accessToken.getPrincipalName()))
                .setDetail(new TokenDetail().setClientIp(accessToken.getClientIp()));
    }


    @Override
    public String getId() {
        return this.token;
    }
}
