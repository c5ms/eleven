package com.eleven.upms.domain;

import com.eleven.upms.configure.UpmsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordManager {

    private final UpmsProperties upmsProperties;
    private final PasswordEncoder passwordEncoder;

    public String defaultPassword() {
        return passwordEncoder.encode(upmsProperties.getDefaultPassword());
    }

    public boolean valid(String raw, String encode) {
        return passwordEncoder.matches(raw, encode);
    }
}
