package com.eleven.upms.domain;

import com.eleven.core.domain.DomainSupport;
import com.eleven.core.exception.ElevenRuntimeException;
import com.eleven.upms.core.UpmsError;
import com.eleven.upms.model.RoleCreateAction;
import com.eleven.upms.model.RoleUpdateAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final AuthorityManager authorityManager;
    private final DomainSupport domainSupport;

    public Collection<Role> listRoles() {
        return roleRepository.findAll();
    }

    public Role createRole(RoleCreateAction action) {
        var id = domainSupport.nextId();
        var role = new Role(id, action);
        validate(role);
        roleRepository.save(role);
        return role;
    }

    public Role updateRole(Role role, RoleUpdateAction action) {
        role.update(action);
        roleRepository.save(role);
        return role;
    }

    private void validate(Role role) throws ElevenRuntimeException {
        // 验证，用户名不能重复
        var exist = roleRepository.findByCode(role.getCode())
                .filter(check -> !StringUtils.equals(check.getId(), role.getId()));
        if (exist.isPresent()) {
            throw UpmsError.ROLE_CODE_REPEAT.exception();
        }
    }

    public Optional<Role> getRole(String id) {
        return roleRepository.findById(id);
    }

    public void deleteRole(Role role) {
        authorityManager.revoke(Authority.powerOfRole(role.getCode()));
        roleRepository.delete(role);
    }
}
