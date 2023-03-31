package com.demcia.eleven.upms.domain.provider;

import com.demcia.eleven.upms.domain.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleUserGrantor implements UserGrantor {
    private final UserRoleRepository userRoleRepository;

    @Override
    public boolean support(Authority authority) {
        // 支持 角色类型
        return StringUtils.equals(authority.getType(), Authority.TYPE_ROLE);
    }

    @Override
    public void grant(User user, Authority authority) {
        var userRole = new UserRole(user, authority.getName());
        var exist = userRoleRepository.existsById(userRole.getId());
        if (!exist) {
            userRoleRepository.save(userRole);
        }
    }

}
