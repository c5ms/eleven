package com.demcia.eleven.upms.application;

import com.demcia.eleven.core.security.Principal;
import com.demcia.eleven.core.security.Token;
import com.demcia.eleven.core.security.TokenDetail;
import com.demcia.eleven.upms.domain.AccessToken;
import com.demcia.eleven.upms.dto.AccessTokenDto;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessTokenConverter {

    private final MapperFacade mapperFacade;


    public AccessTokenDto toDto(AccessToken accessToken) {
        return mapperFacade.map(accessToken, AccessTokenDto.class);
    }


    public Token toToken(AccessToken accessToken) {
        return new Token()
                .setIssuer(accessToken.getIssuer())
                .setValue(accessToken.getToken())
                .setExpireAt(accessToken.getExpireAt())
                .setCreateAt(accessToken.getCreateAt())
                .setPrincipal(new Principal(accessToken.getPrincipalType(), accessToken.getPrincipalName()))
                .setDetail(new TokenDetail().setClientIp(accessToken.getClientIp()));
    }

}
