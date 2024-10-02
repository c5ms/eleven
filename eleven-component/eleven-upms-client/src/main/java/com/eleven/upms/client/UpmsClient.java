package com.eleven.upms.client;

import com.eleven.core.security.Subject;
import com.eleven.core.security.Token;
import com.eleven.core.web.WebConstants;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.core.model.UserDetail;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Primary
@FeignClient(value = UpmsConstants.SERVICE_NAME, path = WebConstants.INNER_API_PREFIX)
public interface UpmsClient {

    @Operation(summary = "read token")
    @GetMapping("/readToken")
    Optional<Token> readToken(@RequestParam("token") String token);

    @Operation(summary = "read user")
    @GetMapping("/readUser")
    Optional<UserDetail> readUser(@RequestParam("id") String id);

    @Operation(summary = "create subject")
    @GetMapping("/createSubject")
    Subject createSubject(@RequestParam("type") String type, @RequestParam("name") String name);
}
