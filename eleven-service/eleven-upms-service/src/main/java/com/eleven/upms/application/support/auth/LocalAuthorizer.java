package com.eleven.upms.application.support.auth;

import com.eleven.core.auth.Authorizer;
import com.eleven.core.auth.Principal;
import com.eleven.upms.domain.manager.AuthorityManager;
import com.eleven.upms.domain.model.Authority;
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
