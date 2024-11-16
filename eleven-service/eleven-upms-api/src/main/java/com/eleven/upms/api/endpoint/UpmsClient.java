package com.eleven.upms.api.endpoint;

import com.eleven.core.authentic.Subject;
import com.eleven.core.authentic.Token;
import com.eleven.core.interfaces.WebConstants;
import com.eleven.upms.api.application.model.UserDetail;
import com.eleven.upms.api.domain.core.UpmsConstants;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Primary
@FeignClient(value = UpmsConstants.SERVICE_NAME, path = WebConstants.API_PREFIX_INTERNAL)
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
