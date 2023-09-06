package com.eleven.security.autoconfigure;

import com.eleven.core.security.PrincipalAuthenticator;
import com.eleven.core.security.PrincipalAuthorizer;
import com.eleven.core.security.SubjectReader;
import com.eleven.core.security.TokenReader;
import com.eleven.security.support.*;
import com.eleven.upms.client.UpmsClient;
import com.eleven.upms.domain.AccessTokenService;
import com.eleven.upms.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityProperties.class)
@Import({SecurityConfigure.None.class, SecurityConfigure.Remote.class, SecurityConfigure.Local.class})
public class SecurityConfigure {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @RequiredArgsConstructor
    @ConditionalOnUseRemoteAuthenticate
    public static class Remote {
        private final UpmsClient upmsClient;

        @Bean
        SubjectReader subjectReader(UpmsClient upmsClient) {
            return new RemoteSubjectReader(upmsClient);
        }

        @Bean
        RemoteTokenReader tokenReader() {
            return new RemoteTokenReader(upmsClient);
        }

        @Bean
        RemoteUserPrincipalAuthenticatorProvider userPrincipalAuthenticatorProvider() {
            return new RemoteUserPrincipalAuthenticatorProvider(upmsClient);
        }

    }

    @RequiredArgsConstructor
    @ConditionalOnUseLocalAuthenticate
    public static class Local {

        @Bean
        @Primary
        @ConditionalOnBean({PrincipalAuthorizer.class, PrincipalAuthenticator.class})
        SubjectReader subjectReader(PrincipalAuthorizer principalAuthorizer,
                                    PrincipalAuthenticator principalAuthenticator) {
            return new LocalSubjectReader(principalAuthorizer, principalAuthenticator);
        }

        @Bean
        @Primary
        @ConditionalOnBean(AccessTokenService.class)
        LocalTokenReader tokenReader(AccessTokenService accessTokenService) {
            return new LocalTokenReader(accessTokenService);
        }

        @Bean
        @Primary
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
