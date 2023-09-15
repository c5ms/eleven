package com.eleven.upms.support;

import com.eleven.core.security.Authorizer;
import com.eleven.core.security.Principal;
import com.eleven.upms.domain.Authority;
import com.eleven.upms.domain.AuthorityManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Primary
@Component
@RequiredArgsConstructor
public class LocalAuthorizer implements Authorizer {
    private final AuthorityManager authorityManager;

    @Override
    public Collection<String> authorize(Principal principal) {
        var owner = Authority.ownerOf(principal);
        var powers = authorityManager.powerOf(owner);
        return powers.stream()
                .map(power -> String.format("%s_%s", StringUtils.toRootUpperCase(power.getType()), power.getName()))
                .collect(Collectors.toList());
    }


}
