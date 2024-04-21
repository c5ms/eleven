package com.eleven.upms.domain;

import com.eleven.core.data.DomainSupport;
import com.eleven.core.exception.ProcessFailureException;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.model.RoleCreateAction;
import com.eleven.upms.model.RoleDto;
import com.eleven.upms.model.RoleUpdateAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleConvertor roleConvertor;
    private final RoleRepository roleRepository;
    private final AuthorityManager authorityManager;
    private final DomainSupport domainSupport;

    public Optional<RoleDto> getRole(String id) {
        var role = roleRepository.findById(id);
        return role.map(roleConvertor::toDto);
    }

    public List<RoleDto> listRoles() {
        return roleRepository.findAll().stream().map(roleConvertor::toDto).collect(Collectors.toList());
    }

    public RoleDto createRole(RoleCreateAction action) {
        var id = domainSupport.getNextId();
        var role = new Role(id, action);
        validate(role);
        roleRepository.save(role);
        return roleConvertor.toDto(role);
    }

    public RoleDto updateRole(String rid, RoleUpdateAction action) {
        var role = roleRepository.requireById(rid);
        role.update(action);
        roleRepository.save(role);
        return roleConvertor.toDto(role);
    }

    public void deleteRole(String rid) {
        var role = roleRepository.requireById(rid);
        authorityManager.revoke(Authority.powerOfRole(role.getCode()));
        roleRepository.delete(role);
    }


    private void validate(Role role) throws ProcessFailureException {
        // 验证，用户名不能重复
        var exist = roleRepository.findByCode(role.getCode())
            .filter(check -> !StringUtils.equals(check.getId(), role.getId()));
        if (exist.isPresent()) {
            throw UpmsConstants.ERROR_ROLE_CODE_REPEAT.exception();
        }
    }


}
