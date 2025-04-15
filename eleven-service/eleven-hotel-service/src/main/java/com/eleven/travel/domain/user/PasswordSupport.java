package com.eleven.travel.domain.user;

import com.eleven.travel.domain.user.configure.UpmsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordSupport {

    private final UpmsProperties upmsProperties;
    private final PasswordEncoder passwordEncoder;

    public String defaultEncodedPassword() {
        return this.encodePassword(upmsProperties.getDefaultPassword());
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean verify(String raw, String encode) {
        return passwordEncoder.matches(raw, encode);
    }
}
