package com.eleven.upms.endpoint.admin;

import com.eleven.framework.interfaces.annonation.AsAdminApi;
import com.eleven.upms.api.application.command.RoleCreateCommand;
import com.eleven.upms.api.application.command.RoleUpdateCommand;
import com.eleven.upms.api.application.model.RoleDetail;
import com.eleven.upms.application.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Tag(name = "role")
@RequestMapping("/roles")
@AsAdminApi
@RequiredArgsConstructor
public class RoleAdminApi {

    private final RoleService roleService;

    @Operation(summary = "get role")
    @GetMapping("/{id}")
    public Optional<RoleDetail> getRole(@PathVariable("id") String id)  {
        return roleService.getRole(id);
    }

    @Operation(summary = "create role")
    @PostMapping
    public RoleDetail createRole(@RequestBody @Validated RoleCreateCommand action) {
        return roleService.createRole(action);
    }

    @Operation(summary = "update role")
    @PutMapping("/{id}")
    public RoleDetail updateRole(@PathVariable("id") String id, @RequestBody RoleUpdateCommand action) {
        return roleService.updateRole(id, action);
    }

    @Operation(summary = "list roles")
    @GetMapping
    public List<RoleDetail> listRoles() {
        return roleService.listRoles();
    }

    @Operation(summary = "delete role")
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable("id") String id) {
        roleService.deleteRole(id);
    }


}
