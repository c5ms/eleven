package com.eleven.travel.domain.user.configure;


import com.eleven.framework.security.Authenticator;
import com.eleven.framework.security.Authorizer;
import com.eleven.framework.security.SubjectCreator;
import com.eleven.travel.domain.user.AuthorityManager;
import com.eleven.travel.domain.user.UserRepository;
import com.eleven.travel.domain.user.support.auth.LocalAuthorizer;
import com.eleven.travel.domain.user.support.auth.LocalSubjectCreator;
import com.eleven.travel.domain.user.support.auth.UserAuthenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

@Configuration(proxyBeanMethods = false)
public class UpmsApplicationConfigure {

    @Bean
    SubjectCreator subjectCreator(Collection<Authorizer> authorizers, Collection<Authenticator> authenticators) {
        return new LocalSubjectCreator(authorizers, authenticators);
    }

    @Bean
    Authorizer authorizer(AuthorityManager authorityManager) {
        return new LocalAuthorizer(authorityManager);
    }

    @Bean
    Authenticator authenticator(UserRepository userRepository) {
        return new UserAuthenticator(userRepository);
    }

}
