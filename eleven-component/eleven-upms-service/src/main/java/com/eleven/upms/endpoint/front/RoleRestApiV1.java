package com.eleven.upms.endpoint.front;

import com.eleven.core.exception.RequireDataNotFoundException;
import com.eleven.core.web.annonation.AsRestApi;
import com.eleven.upms.action.RoleCreateAction;
import com.eleven.upms.action.RoleUpdateAction;
import com.eleven.upms.domain.RoleService;
import com.eleven.upms.dto.RoleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "角色")
@RequestMapping("/roles")
@AsRestApi
@RequiredArgsConstructor
public class RoleRestApiV1 {

    private final RoleService roleService;

    @Operation(summary = "角色读取")
    @GetMapping("/{id}")
    public RoleDto getRole(@PathVariable("id") String id) {
        return roleService.getRole(id).orElseThrow(RequireDataNotFoundException::new);
    }

    @Operation(summary = "角色创建")
    @PostMapping
    public RoleDto createRole(@RequestBody @Validated RoleCreateAction action) {
        return roleService.createRole(action);
    }

    @Operation(summary = "角色更新")
    @PutMapping("/{id}")
    public RoleDto updateRole(@PathVariable("id") String id, @RequestBody RoleUpdateAction action) {
        return roleService.updateRole(id, action);
    }

    @Operation(summary = "角色列表")
    @GetMapping
    public List<RoleDto> queryUserPage() {
        return roleService.listRoles();
    }

    @Operation(summary = "角色删除")
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable("id") String id) {
        roleService.deleteRole(id);
    }


}
