package com.demcia.eleven.upms.client;

import com.demcia.eleven.core.pageable.PaginationResult;
import com.demcia.eleven.upms.core.action.UserCreateAction;
import com.demcia.eleven.upms.core.action.UserQueryAction;
import com.demcia.eleven.upms.core.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(
        name = "upms",
        url = "${service.eleven-upms.url:}",
        path = "${service.eleven-upms.path:/users}"
)
public interface UserResourceClient {

    @PostMapping
    UserDto createUser(@RequestBody @Validated UserCreateAction action);

    @GetMapping("/{id}")
    Optional<UserDto> getUser(@PathVariable("id") String id);

    @GetMapping
    PaginationResult<UserDto> queryUser(@SpringQueryMap UserQueryAction queryAction);

}
