package com.eleven.domain.user;

import com.eleven.domain.user.model.User;
import com.eleven.domain.user.model.UserRepository;
import com.eleven.domain.user.support.PasswordSupport;
import com.eleven.framework.authentic.Principal;
import com.eleven.framework.data.DomainException;
import com.eleven.framework.data.DomainHelper;
import com.eleven.upms.api.domain.core.UpmsConstants;
import com.eleven.upms.api.domain.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserManager {

    private final PasswordSupport passwordSupport;
    private final UserRepository userRepository;

    private final List<UserValidator> userValidators;

    public Principal login(String login, String password) {
        var userOptional = userRepository.findByUsername(login);
        if (userOptional.isEmpty()) {
            throw UpmsConstants.ERROR_LOGIN_PASSWORD.toException();
        }
        var user = userOptional.get();
        var pass = passwordSupport.verify(password, user.getPassword());
        if (!pass) {
            throw UpmsConstants.ERROR_LOGIN_PASSWORD.toException();
        }
        if (user.isDeleted()) {
            throw UpmsConstants.ERROR_USER_ALREADY_DELETED.toException();
        }
        user.login();
        userRepository.save(user);
        return userOptional.get().toPrincipal();
    }

    /**
     * it will persist the user and publish the domain event
     *
     * @param user the new user
     */
    public void create(User user) {
        validate(user);
        user.changePassword(passwordSupport.defaultEncodedPassword());
        userRepository.save(user);
        DomainHelper.publishEvent(new UserCreatedEvent(user.getId()));
    }

    private void validate(User user) throws DomainException {
        for (UserValidator userValidator : userValidators) {
            userValidator.validate(user);
        }
    }

}
