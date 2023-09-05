package com.eleven.upms.endpoint;

import com.eleven.core.rest.annonation.RestResource;
import com.eleven.core.rest.exception.NotFoundException;
import com.eleven.upms.domain.Role;
import com.eleven.upms.domain.RoleConvertor;
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
import java.util.stream.Collectors;

@Slf4j
@Tag(name = "角色")
@RequestMapping("/roles")
@RestResource
@RequiredArgsConstructor
public class RoleResourceV1 {

    private final RoleService roleService;
    private final RoleConvertor roleConvertor;

    @Operation(summary = "角色读取")
    @GetMapping("/{id}")
    public RoleDto getRole(@PathVariable("id") String id) {
        var role = requireRole(id);
        return roleConvertor.toDto(role);
    }

    @Operation(summary = "角色创建")
    @PostMapping
    public RoleDto createRole(@RequestBody @Validated RoleCreateAction action) {
        var role = roleService.createRole(action);
        return roleConvertor.toDto(role);
    }

    @Operation(summary = "角色更新")
    @PostMapping("/{id}")
    public RoleDto updateRole(@PathVariable("id") String id, @RequestBody RoleUpdateAction action) {
        var user = requireRole(id);
        roleService.updateRole(user, action);
        return roleConvertor.toDto(user);
    }

    @Operation(summary = "角色列表")
    @GetMapping
    public List<RoleDto> queryUserPage() {
        return roleService.listRoles()
                .stream()
                .map(roleConvertor::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "角色删除")
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable("id") String id) {
        var role = requireRole(id);
        roleService.deleteRole(role);
    }


    private Role requireRole(String id) {
        return roleService.getRole(id).orElseThrow(NotFoundException::new);
    }

}
