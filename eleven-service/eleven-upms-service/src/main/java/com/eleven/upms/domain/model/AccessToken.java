package com.eleven.upms.domain.model;

import com.eleven.core.data.AbstractEntity;
import com.eleven.core.security.Principal;
import com.eleven.core.security.ToPrincipal;
import com.eleven.core.security.Token;
import com.eleven.core.security.TokenDetail;
import com.eleven.core.time.TimeContext;
import com.eleven.upms.api.domain.event.AccessTokenCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Table("upms_access_token")
@With
@Getter
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class AccessToken extends AbstractEntity {

    @Id
    @Column("token")
    private String token;

    @Column("issuer")
    private String issuer;

    @Column("create_at")
    private LocalDateTime createAt;

    @Column("expire_at")
    private LocalDateTime expireAt;

    @Embedded.Nullable
    private Owner owner;

    @Column("client_ip")
    private String clientIp;

    public AccessToken(Token token) {
        this.token = token.getValue();
        this.owner = new Owner(token.getPrincipal());
        this.clientIp = token.getDetail().getClientIp();
        this.issuer = token.getIssuer();
        this.expireAt = token.getExpireAt();
        this.createAt = token.getCreateAt();
        this.addEvent(new AccessTokenCreatedEvent(token.getValue()));
        super.markNew();
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
            .setPrincipal(accessToken.getOwner().toPrincipal())
            .setDetail(new TokenDetail().setClientIp(accessToken.getClientIp()));
    }

    public void expire() {
        this.expireAt = TimeContext.localDateTime();
    }

    @Override
    public String getId() {
        return this.token;
    }

    @Getter
    @AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
    public static class Owner implements ToPrincipal {

        @Column("principal_name")
        private String name;

        @Column("principal_type")
        private String type;

        public Owner(Principal principal) {
            this.name = principal.getName();
            this.type = principal.getType();
        }

        @Override
        public Principal toPrincipal() {
            return new Principal(this.type, this.name);
        }
    }
}
