package com.eleven.demo.endpoint.rest;

import com.eleven.core.rest.annonation.AsRestApi;
import com.eleven.core.security.SecurityContext;
import com.eleven.core.security.Subject;
import com.eleven.upms.client.UpmsClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/demo")
@AsRestApi
public class DemoApi {
    private final UpmsClient upmsClient;

    @GetMapping("/01")
    public Subject queryUserPage() {
        var subject = SecurityContext.getCurrentSubject();
//        System.out.println(subject);
//        return upmsClient.readUser("655620230829173521000001");
        return subject;
    }

}
