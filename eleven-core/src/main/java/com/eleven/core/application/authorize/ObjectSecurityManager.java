package com.eleven.core.application.authorize;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ObjectSecurityManager {

    private final List<ObjectAuthorizer> authorizers;

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
            throw new NoReadAuthorityException();
        }
        return true;
    }

    public boolean mustWritable(Object resource) throws NoWriteAuthorityException {
        if (!isWritable(resource)) {
            throw new NoWriteAuthorityException();
        }
        return true;
    }

}
