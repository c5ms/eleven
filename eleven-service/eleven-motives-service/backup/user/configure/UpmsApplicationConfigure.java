package com.motiveschina.hotel.features.user.configure;


import java.util.Collection;
import com.eleven.framework.security.Authenticator;
import com.eleven.framework.security.Authorizer;
import com.eleven.framework.security.SubjectCreator;
import com.motiveschina.hotel.features.user.support.auth.LocalSubjectCreator;
import com.motiveschina.hotel.features.user.AuthorityManager;
import com.motiveschina.hotel.features.user.UserRepository;
import com.motiveschina.hotel.features.user.support.auth.LocalAuthorizer;
import com.motiveschina.hotel.features.user.support.auth.UserAuthenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
