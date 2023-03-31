package com.demcia.eleven.upms.domain;

import lombok.Getter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Getter
public class AuthoritySet extends HashSet<Authority> {
    private final Set<Authority> authorities = new HashSet<>();

    public void addRoles(Collection<String> roles) {
        roles.stream()
                .map(Authority::ofRole)
                .forEach(authorities::add);
    }

    public void addPermissions(Collection<String> permissions) {
        permissions.stream()
                .map(Authority::ofPermission)
                .forEach(authorities::add);
    }

}