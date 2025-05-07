package com.motiveschina.hotel.features.user.support.auth;

import java.util.Collection;
import java.util.stream.Collectors;
import com.eleven.framework.security.Authorizer;
import com.eleven.framework.security.Principal;
import com.motiveschina.hotel.features.user.Authority;
import com.motiveschina.hotel.features.user.AuthorityManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class LocalAuthorizer implements Authorizer {
    private final AuthorityManager authorityManager;

    @Override
    public Collection<String> authorize(Principal principal) {
        var owner = Authority.ownerOf(principal);
        var powers = authorityManager.authoritiesOf(owner);
        return powers.stream()
                .map(Authority::getPower)
                .map(power -> String.format("%s_%s", StringUtils.toRootUpperCase(power.getType()), power.getName()))
                .collect(Collectors.toList());
    }

}
