package com.eleven.upms.application.service;

import com.eleven.upms.core.command.RoleCreateCommand;
import com.eleven.upms.core.command.RoleUpdateCommand;
import com.eleven.upms.core.model.RoleDetail;
import com.eleven.upms.domain.service.RoleManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleManager roleManager;

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Optional<RoleDetail> getRole(String id) {
        return roleManager.getRole(id);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<RoleDetail> listRoles() {
        return roleManager.listRoles();
    }

    @Transactional(rollbackFor = Exception.class)
    public RoleDetail createRole(RoleCreateCommand action) {
        return roleManager.createRole(action);
    }

    @Transactional(rollbackFor = Exception.class)
    public RoleDetail updateRole(String rid, RoleUpdateCommand action) {
        return roleManager.updateRole(rid, action);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(String rid) {
        roleManager.deleteRole(rid);
    }
}
