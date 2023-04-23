package com.eleven.upms.domain;

import com.eleven.core.domain.AbstractDomain;
import com.eleven.core.domain.AuditMetadata;
import com.eleven.upms.domain.action.UserCreateAction;
import com.eleven.upms.domain.action.UserUpdateAction;
import com.eleven.upms.domain.event.UserCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("upms_user")
@Getter
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class User extends AbstractDomain<User> {

    public static final String TYPE_INNER_USER = "user";

    @Column("login")
    private final String login;

    @Id
    @Column("id")
    private String id;

    @Version
    @Column("version")
    private Integer version;

    /**
     * 用户类型，用于标记用户是什么系统的用户
     */
    @Column("type")
    private String type;

    @Column("password")
    private String password;

    @Column("nickname")
    private String nickname;

    @Column("state")
    private UserState state = UserState.NORMAL;

    @Embedded.Nullable
    private AuditMetadata audit = new AuditMetadata();

    /**
     * 创建新用户
     *
     * @param action 创建指令
     */
    public User(String id, UserCreateAction action) {
        this.id = id;
        this.login = action.getLogin();
        this.nickname = action.getNickname();
        this.type = TYPE_INNER_USER;
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
    }

    /**
     * 设置用户密码
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 系统用户状态
     */
    @Getter
    @RequiredArgsConstructor
    public enum UserState {
        NORMAL("正常"),
        DISABLED("禁用"),
        READONLY("只读");

        private final String label;
    }


}
