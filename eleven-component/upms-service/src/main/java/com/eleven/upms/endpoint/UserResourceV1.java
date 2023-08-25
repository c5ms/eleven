package com.eleven.upms.endpoint;

import com.eleven.core.model.PaginationResult;
import com.eleven.core.rest.annonation.RestResource;
import com.eleven.core.rest.exception.NotFoundException;
import com.eleven.upms.domain.*;
import com.eleven.upms.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "用户")
@RequestMapping("/users")
@RestResource
@RequiredArgsConstructor
public class UserResourceV1 {

    private final UserService userService;
    private final AuthorityManager authorityManager;

    private final UpmsConvertor upmsConvertor;

    @Operation(summary = "用户查询")
    @GetMapping
    public PaginationResult<UserDto> queryUserPage(@ParameterObject UserFilter filter) {
        return userService.queryUserPage(filter).map(upmsConvertor::toDto);
    }

    @Operation(summary = "用户读取")
    @GetMapping("/{id}")
    public UserDetail getUser(@PathVariable("id") String id) {
        var user = requireUser(id);
        return upmsConvertor.toDetail(user);
    }

    @Operation(summary = "用户创建")
    @PostMapping
    public UserDto createUser(@RequestBody @Validated UserCreateAction action) {
        var user = userService.createUser(action);
        return upmsConvertor.toDto(user);
    }

    @Operation(summary = "用户更新")
    @PostMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateAction action) {
        var user = requireUser(id);
        userService.updateUser(user, action);
        return upmsConvertor.toDto(user);
    }

    @Operation(summary = "用户删除")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        var user = requireUser(id);
        userService.deleteUser(user);
    }


    @Operation(summary = "用户权限表")
    @GetMapping("/{id}/authorities")
    public List<PowerDto> getUserAuthorities(@PathVariable("id") String id) {
        var user = requireUser(id);
        var owner = Authority.ownerOfUser(user.getId());
        return authorityManager.powerOf(owner)
                .stream()
                .map(upmsConvertor::toDto)
                .toList();

    }


    @Operation(summary = "用户锁定")
    @PostMapping("/{id}/_lock")
    public UserDto lockUser(@PathVariable("id") String id) {
        var user = requireUser(id);
        userService.lockUser(user);
        return upmsConvertor.toDto(user);
    }

    @Operation(summary = "用户解锁")
    @PostMapping("/{id}/_unlock")
    public UserDto unlockUser(@PathVariable("id") String id) {
        var user = requireUser(id);
        userService.unlockUser(user);
        return upmsConvertor.toDto(user);
    }


    private User requireUser(String id) {
        return userService.getUser(id).orElseThrow(NotFoundException::new);
    }
}
