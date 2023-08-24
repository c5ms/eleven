package com.eleven.security.autoconfigure;

import com.eleven.core.security.TokenReader;
import com.eleven.security.support.LocalTokenReader;
import com.eleven.security.support.LocalUserPrincipalAuthenticatorProvider;
import com.eleven.security.support.RemoteTokenReader;
import com.eleven.security.support.RemoteUserPrincipalAuthenticatorProvider;
import com.eleven.upms.client.AccessTokenClient;
import com.eleven.upms.client.UserClient;
import com.eleven.upms.domain.AccessTokenService;
import com.eleven.upms.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


@EnableCaching
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityProperties.class)
@Import({SecurityConfigure.None.class,SecurityConfigure.Remote.class, SecurityConfigure.Local.class})
public class SecurityConfigure {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }


    @RequiredArgsConstructor
    @ConditionalOnUseRemoteAuthenticate
    public static class Remote {
        private final UserClient userClient;
        private final AccessTokenClient accessTokenClient;

        @Bean
        RemoteTokenReader tokenReader() {
            return new RemoteTokenReader(accessTokenClient);
        }

        @Bean
        RemoteUserPrincipalAuthenticatorProvider userPrincipalAuthenticatorProvider() {
            return new RemoteUserPrincipalAuthenticatorProvider(userClient);
        }

    }

    @RequiredArgsConstructor
    @ConditionalOnUseLocalAuthenticate
    public static class Local {

        @Bean
        @ConditionalOnBean(AccessTokenService.class)
        LocalTokenReader tokenReader(AccessTokenService accessTokenService) {
            return new LocalTokenReader(accessTokenService);
        }

        @Bean
        @ConditionalOnBean(UserService.class)
        LocalUserPrincipalAuthenticatorProvider userPrincipalAuthenticatorProvider(UserService userService) {
            return new LocalUserPrincipalAuthenticatorProvider(userService);
        }


    }

    @RequiredArgsConstructor
    public static class None {

        @Bean
        @ConditionalOnMissingBean(TokenReader.class)
          TokenReader unSupportTokenReader() {
            return token -> Optional.empty();
        }

    }


}
