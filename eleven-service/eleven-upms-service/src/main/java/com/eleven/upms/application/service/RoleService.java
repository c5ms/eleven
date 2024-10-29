package com.eleven.upms.application.service;

import com.eleven.core.domain.DomainHelper;
import com.eleven.upms.api.application.command.RoleCreateCommand;
import com.eleven.upms.api.application.command.RoleUpdateCommand;
import com.eleven.upms.api.application.model.RoleDetail;
import com.eleven.upms.application.convert.RoleConvertor;
import com.eleven.upms.domain.manager.AuthorityManager;
import com.eleven.upms.domain.manager.RoleManager;
import com.eleven.upms.domain.model.Authority;
import com.eleven.upms.domain.model.Role;
import com.eleven.upms.domain.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {


    private final RoleManager roleManager;
    private final AuthorityManager authorityManager;

    private final RoleConvertor roleConvertor;
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Optional<RoleDetail> getRole(String id) {
        var role = roleRepository.findById(id);
        return role.map(roleConvertor::toDetail);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<RoleDetail> listRoles() {
        return roleRepository.findAll()
            .stream()
            .map(roleConvertor::toDetail)
            .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public RoleDetail createRole(RoleCreateCommand action) {
        var role = Role.builder()
            .id(DomainHelper.nextId())
            .code(action.getCode())
            .name(action.getName())
            .build();
        roleManager.validate(role);
        roleRepository.save(role);
        return roleConvertor.toDetail(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public RoleDetail updateRole(String rid, RoleUpdateCommand action) {
        var role = roleRepository.requireById(rid);
        role.update(action.getCode(), action.getName());
        roleRepository.save(role);
        return roleConvertor.toDetail(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(String rid) {
        var role = roleRepository.requireById(rid);
        authorityManager.revoke(Authority.powerOfRole(role.getCode()));
        roleRepository.delete(role);
    }
}
