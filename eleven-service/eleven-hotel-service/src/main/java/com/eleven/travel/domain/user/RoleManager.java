package com.eleven.travel.domain.user;

import com.eleven.domain.user.model.Role;
import com.eleven.domain.user.model.RoleRepository;
import com.eleven.framework.domain.DomainException;
import com.eleven.upms.api.domain.core.UpmsConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleManager {

    private final RoleRepository roleRepository;

    public void validate(Role role) throws DomainException {
        // 验证，用户名不能重复
        var exist = roleRepository.findByCode(role.getCode())
                .filter(check -> !StringUtils.equals(check.getId(), role.getId()));
        if (exist.isPresent()) {
            throw UpmsConstants.ERROR_ROLE_CODE_REPEAT.toException();
        }
    }


}
