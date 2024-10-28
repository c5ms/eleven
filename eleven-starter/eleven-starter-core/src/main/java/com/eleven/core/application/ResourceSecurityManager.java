package com.eleven.core.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResourceSecurityManager {

    private final List<ResourceAuthorizer> authorizers;

    public boolean isReadable(Object resource) {
        return authorizers.stream()
                .filter(resourceAuthorizer -> resourceAuthorizer.support(resource))
                .allMatch(resourceAuthorizer -> resourceAuthorizer.isReadable(resource));
    }

    public boolean isWritable(Object resource) {
        return authorizers.stream()
                .filter(resourceAuthorizer -> resourceAuthorizer.support(resource))
                .allMatch(resourceAuthorizer -> resourceAuthorizer.isWritable(resource));
    }

    public boolean mustReadable(Object resource) throws NoReadPermissionException {
        if (!isReadable(resource)) {
            throw NoReadPermissionException.instance();
        }
        return true;
    }

    public boolean mustWritable(Object resource) throws NoWritePermissionException {
        if (!isWritable(resource)) {
            throw NoWritePermissionException.instance();
        }
        return true;
    }

}
