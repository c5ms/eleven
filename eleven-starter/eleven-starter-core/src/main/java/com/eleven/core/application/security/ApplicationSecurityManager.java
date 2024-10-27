package com.eleven.core.application.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationSecurityManager {

    private final List<ResourceAuthorizer> authorizers;

    public boolean isReadable(Object resource) {
        return authorizers.stream()
            .filter(resourceAuthorizer -> resourceAuthorizer.support(resource))
            .allMatch(resourceAuthorizer -> resourceAuthorizer.isReadable(resource));
    }

    public boolean isWritable(Object resource) {
        return authorizers.stream()
            .filter(resourceAuthorizer -> resourceAuthorizer.support(resource))
            .allMatch(resourceAuthorizer -> resourceAuthorizer.isReadable(resource));
    }

}
