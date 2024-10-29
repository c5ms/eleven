package com.eleven.core.application.secure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DomainSecurityManager {

    private final List<DomainAuthorizer> authorizers;

    public boolean isReadable(Object resource) {
        return authorizers.stream()
                .filter(aggregateAuthorizer -> aggregateAuthorizer.support(resource))
                .allMatch(aggregateAuthorizer -> aggregateAuthorizer.isReadable(resource));
    }

    public boolean isWritable(Object resource) {
        return authorizers.stream()
                .filter(aggregateAuthorizer -> aggregateAuthorizer.support(resource))
                .allMatch(aggregateAuthorizer -> aggregateAuthorizer.isWritable(resource));
    }

    public boolean mustReadable(Object resource) throws NoReadAuthorityException {
        if (!isReadable(resource)) {
            throw NoReadAuthorityException.instance();
        }
        return true;
    }

    public boolean mustWritable(Object resource) throws NoWriteAuthorityException {
        if (!isWritable(resource)) {
            throw NoWriteAuthorityException.instance();
        }
        return true;
    }

}
