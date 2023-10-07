package com.eleven.upms.domain;

import com.eleven.core.domain.AbstractAuditableDomain;
import com.eleven.core.security.Principal;
import com.eleven.core.security.ToPrincipal;
import com.eleven.core.time.TimeContext;
import com.eleven.upms.action.UserCreateAction;
import com.eleven.upms.action.UserUpdateAction;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Table("upms_user")
@Getter
@FieldNameConstants
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class User extends AbstractAuditableDomain implements ToPrincipal, Serializable {

    public static final String TYPE_INNER_USER = "user";

    @Id
    @Column("id")
    private final String id;

    @Version
    @Column("version")
    private Integer version;

    @Column("type")
    private String type;

    @Column("nickname")
    private String nickname;

    @Column("username")
    private String username;

    @Column("password")
    private String password;

    @Column("state")
    private UserState state;

    @Column("is_locked")
    private Boolean isLocked;

    @Column("register_at")
    private LocalDateTime registerAt;

    @Column("login_at")
    private LocalDateTime loginAt;

    /**
     * 创建新用户
     *
     * @param action 创建指令
     */
    public User(String id, UserCreateAction action) {
        this.id = id;
        this.username = action.getUsername();
        this.nickname = action.getNickname();
        this.type = TYPE_INNER_USER;
        this.isLocked = false;
        this.state = ObjectUtils.defaultIfNull(action.getState(), UserState.NORMAL);
        this.registerAt = TimeContext.localDateTime();
        super.markNew();
    }

    /**
     * 更新用户
     *
     * @param action 更新指令
     */
    public void update(UserUpdateAction action) {
        if (Objects.nonNull(action.getNickname())) {
            this.nickname = StringUtils.trim(action.getNickname());
        }
        if (Objects.nonNull(action.getState())) {
            this.state = action.getState();
        }
    }

    /**
     * 设置用户密码
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 锁定用户
     */
    public void lock() {
        this.isLocked = true;
    }

    /**
     * 解锁用户
     */
    public void unlock() {
        this.isLocked = false;
    }


    /**
     * 登入
     */
    public void login() {
        this.loginAt = TimeContext.localDateTime();
    }

    @Override
    public Principal toPrincipal() {
        return new Principal(UpmsConstants.PRINCIPAL_TYPE_USER, this.getId());
    }
}
