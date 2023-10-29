package com.eleven.upms.client;

import com.eleven.core.security.Subject;
import com.eleven.core.security.Token;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.model.UserDto;
import com.eleven.upms.model.UserState;
import com.eleven.upms.model.UserSummary;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Primary
@FeignClient(value = UpmsConstants.SERVICE_NAME)
public interface UpmsClient {

    // unused
//    @Operation(summary = "读取令牌")
//    @GetMapping("/readToken")
//    Optional<Token> readToken(@RequestParam("token") String token);

    @Operation(summary = "get user")
    @GetMapping("/readUser")
    Optional<UserDto> readUser(@RequestParam("id") String id);

    @Operation(summary = "get user summary")
    @GetMapping("/readUserSummary")
    Optional<UserSummary> readUserSummary(@RequestParam("id") String id);

    @Operation(summary = "create subject")
    @GetMapping("/createSubject")
    Subject createSubject(@RequestParam("type") String type, @RequestParam("name") String name);
}
