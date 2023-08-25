package com.eleven.upms.domain;

import com.eleven.core.domain.AbstractAuditableDomain;
import com.eleven.core.time.TimeContext;
import com.eleven.upms.enums.UserState;
import com.eleven.upms.model.*;
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

import java.time.LocalDateTime;
import java.util.Objects;

@Table("upms_user")
@Getter
@FieldNameConstants
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class User extends AbstractAuditableDomain<User> {

    public static final String TYPE_INNER_USER = "user";

    @Id
    @Column("id")
    private String id;

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
        super.andEvent(new UserCreatedEvent(id));
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
        super.andEvent(new UserUpdatedEvent(id));
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
        super.andEvent(new UserLockedEvent(id));
    }

    /**
     * 解锁用户
     */
    public void unlock() {
        this.isLocked = false;
        super.andEvent(new UserUnLockedEvent(id));
    }

    /**
     * 删除
     */
    public void delete() {
        super.andEvent(new UserDeletedEvent(id));
    }

    /**
     * 登入
     */
    public void login() {
        super.andEvent(new userLoginEvent(id));
        this.loginAt=TimeContext.localDateTime();
    }
}
