package com.eleven.upms.application.configure;

import com.eleven.framework.authentic.Authenticator;
import com.eleven.framework.authentic.Authorizer;
import com.eleven.framework.authentic.SubjectCreator;
import com.eleven.upms.application.support.auth.LocalAuthorizer;
import com.eleven.upms.application.support.auth.LocalSubjectCreator;
import com.eleven.upms.application.support.auth.UserAuthenticator;
import com.eleven.upms.domain.manager.AuthorityManager;
import com.eleven.upms.domain.model.UserRepository;
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
