package com.eleven.core.authorization;

import com.eleven.core.time.TimeHelper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 当前系统访问授权概要
 */
@Data
public class Subject implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String ANONYMOUS_SUBJECT_NICK_NAME = "匿名用户";
    private static final String ANONYMOUS_SUBJECT_USER_ID = "anonymous";
    private static final Set<String> ANONYMOUS_AUTHORITIES = Set.of("ROLE_anonymous");

    public static final Subject ANONYMOUS_INSTANCE = new Subject(
        ANONYMOUS_SUBJECT_USER_ID,
        ANONYMOUS_SUBJECT_NICK_NAME,
        null,
        ANONYMOUS_AUTHORITIES
    );

    /**
     * 用户主体身份
     */
    private Principal principal;

    /**
     * 系统用户 ID
     */
    private String userId;

    /**
     * 授权名称，通常是可以给用户看的名称，比如昵称
     */
    private String nickName;

    /**
     * 可用权限,构造后不要写入任何数据
     */
    private Set<String> authorities;

    /**
     * 创建时间
     */
    private LocalDateTime createAt = TimeHelper.localDateTime();


    public Subject() {
    }

    public Subject(String userId, String name, Principal principal, Set<String> authorities) {
        this.userId = userId;
        this.nickName = name;
        this.principal = principal;
        this.authorities = authorities;
    }

    /**
     * 授权
     *
     * @param authorities 权限
     */
    public void grant(Set<String> authorities) {
        this.authorities.addAll(authorities);
    }

    /**
     * 重新授权
     *
     * @param authorities 权限
     */
    public void reGrant(Set<String> authorities) {
        this.authorities.addAll(authorities);
    }


    public boolean isAnonymous() {
        return ANONYMOUS_SUBJECT_NICK_NAME.equals(nickName);
    }

}
