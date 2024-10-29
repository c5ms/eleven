package com.eleven.upms.domain.model;

import com.eleven.core.data.AbstractEntity;
import com.eleven.core.data.Audition;
import com.eleven.core.data.LogicDeletable;
import com.eleven.core.auth.Principal;
import com.eleven.core.auth.ToPrincipal;
import com.eleven.core.time.TimeHelper;
import com.eleven.upms.api.domain.event.UserDeletedEvent;
import com.eleven.upms.api.domain.event.UserLoginEvent;
import com.eleven.upms.api.domain.event.UserStatusChangedEvent;
import com.eleven.upms.api.domain.model.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table("upms_user")
@Getter
@FieldNameConstants
@Builder
@AllArgsConstructor(onConstructor_ = {@PersistenceCreator})
public class User extends AbstractEntity implements ToPrincipal, Serializable, LogicDeletable {

    public static final String USER_TYPE_ADMIN = "admin";
    public static final String PRINCIPAL_TYPE_USER = "user";

    @Id
    @Column("id")
    private final String id;

    @Column("type")
    private String type;

    @Column("username")
    private String username;

    @Column("password")
    private String password;

    @Column("status")
    private UserStatus status;

    @Column("description")
    private String description;

    @Column("is_locked")
    private Boolean isLocked;

    @Column("register_at")
    private LocalDateTime registerAt;

    @Column("login_at")
    private LocalDateTime loginAt;

    @Column("delete_at")
    private LocalDateTime deleteAt;

    @Embedded.Empty
    private Audition audition;

    public void changeStatus(UserStatus status) {
        this.status = status;
        this.addEvent(new UserStatusChangedEvent(id));
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void deleted() {
        this.deleteAt = TimeHelper.localDateTime();
        this.addEvent(new UserDeletedEvent(id));
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
        this.loginAt = TimeHelper.localDateTime();
        this.addEvent(new UserLoginEvent(id));
    }

    @Override
    public Principal toPrincipal() {
        return new Principal(PRINCIPAL_TYPE_USER, this.getId());
    }
}
