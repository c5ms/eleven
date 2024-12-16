package com.eleven.domain.user.support.auth;

import com.eleven.domain.user.Authority;
import com.eleven.domain.user.AuthorityManager;
import com.eleven.framework.security.Authorizer;
import com.eleven.framework.security.Principal;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.stream.Collectors;

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
