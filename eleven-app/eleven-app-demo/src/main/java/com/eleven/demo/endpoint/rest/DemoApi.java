package com.eleven.demo.endpoint.rest;

import com.eleven.core.security.SecurityContext;
import com.eleven.core.security.Subject;
import com.eleven.core.web.annonation.AsFrontApi;
import com.eleven.upms.client.UpmsClient;
import com.eleven.upms.core.model.UserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/demo")
@AsFrontApi
public class DemoApi {
    private final UpmsClient upmsClient;

    //    @RolesAllowed("user")
    @GetMapping("/01")
    public Subject _01() {
        return SecurityContext.getCurrentSubject();
    }

    //    @RolesAllowed("user")
    @GetMapping("/02")
    public Optional<UserDetail> _02() {
        return upmsClient.readUser("032620241002232046000001");
    }

}
