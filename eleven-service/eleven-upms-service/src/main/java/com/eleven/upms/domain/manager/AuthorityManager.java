package com.eleven.upms.domain.manager;

import com.eleven.core.domain.DomainUtils;
import com.eleven.upms.domain.model.Authority;
import com.eleven.upms.domain.model.AuthorityRepository;
import com.eleven.upms.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorityManager {

    private final AuthorityRepository authorityRepository;


    /**
     * grant roles to a user
     * @param user is the user
     * @param roles is the roles
     */
    public void grant(User user, List<String> roles) {
        var owner = Authority.ownerOf(user.toPrincipal());
        var powers = roles.stream().map(Authority::powerOfRole).toList();

        revoke(owner, Authority.POWER_ROLE);
        grant(owner, powers);
    }

    /**
     * 授权
     *
     * @param owner  授权对象
     * @param powers 权利
     */
    public void grant(Authority.Owner owner, List<Authority.Power> powers) {
        var authorities = powers.stream().map(power -> {
            var id = DomainUtils.nextId();
            return new Authority(id, owner, power);
        }).toList();
        authorityRepository.saveAll(authorities);
    }

    /**
     * 撤销某持有者所有此授权
     *
     * @param owner 持有者
     * @param power 权利
     */
    public void revoke(Authority.Owner owner, String power) {
        authorityRepository.deleteByOwnerAndPowerType(owner.getType(), owner.getName(), power);
    }

    /**
     * 撤销某持有者所有授权
     *
     * @param owner 持有者
     */
    public void revoke(Authority.Owner owner) {
        authorityRepository.deleteByOwner(owner.getType(), owner.getName());
    }

    /**
     * 撤销某个权限所有授权
     *
     * @param power 权限
     */
    public void revoke(Authority.Power power) {
        authorityRepository.deleteByPower(power.getType(), power.getName());
    }


    /**
     * 读取某持有者所有授权
     *
     * @param owner 持有者
     * @return 所有授权
     */
    public List<Authority> authoritiesOf(Authority.Owner owner) {
        return authorityRepository.findByOwner(owner).stream().toList();
    }

    /**
     * 读取某持有者所有指定类型的权限
     *
     * @param owner 持有者
     * @param power 权限类型
     * @return 权限表
     */
    public List<Authority> authoritiesOf(Authority.Owner owner, String... power) {
        return authorityRepository.findByOwner(owner.getType(), owner.getName(), Set.of(power)).stream().toList();
    }

}
