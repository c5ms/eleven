package com.eleven.upms.endpoint.front;

import com.eleven.core.exception.DataNotFoundException;
import com.eleven.core.web.annonation.AsRestApi;
import com.eleven.upms.domain.RoleService;
import com.eleven.upms.model.RoleCreateAction;
import com.eleven.upms.model.RoleDto;
import com.eleven.upms.model.RoleUpdateAction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "role")
@RequestMapping("/roles")
@AsRestApi
@RequiredArgsConstructor
public class RoleRestApiV1 {

    private final RoleService roleService;

    @Operation(summary = "get role")
    @GetMapping("/{id}")
    public RoleDto getRole(@PathVariable("id") String id) {
        return roleService.getRole(id).orElseThrow(DataNotFoundException::new);
    }

    @Operation(summary = "create role")
    @PostMapping
    public RoleDto createRole(@RequestBody @Validated RoleCreateAction action) {
        return roleService.createRole(action);
    }

    @Operation(summary = "update role")
    @PutMapping("/{id}")
    public RoleDto updateRole(@PathVariable("id") String id, @RequestBody RoleUpdateAction action) {
        return roleService.updateRole(id, action);
    }

    @Operation(summary = "list roles")
    @GetMapping
    public List<RoleDto> listRoles() {
        return roleService.listRoles();
    }

    @Operation(summary = "delete role")
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable("id") String id) {
        roleService.deleteRole(id);
    }


}
