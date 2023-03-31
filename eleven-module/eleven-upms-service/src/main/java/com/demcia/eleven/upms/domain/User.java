package com.demcia.eleven.upms.domain;

import com.demcia.eleven.upms.domain.action.UserCreateAction;
import com.demcia.eleven.upms.domain.action.UserUpdateAction;
import com.demcia.eleven.upms.domain.enums.UserState;
import com.demcia.eleven.upms.domain.event.UserCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;


@Table("upms_user")
@With
@Getter
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class User extends AbstractAggregateRoot<User> {

    @Id
    @Column("id_")
    private final String id;

    @Column("login_")
    private String login;

    /**
     * 如果用户类型不是 system，表示上一个外部系统用户，则会有对应的外部系统用户 ID
     */
    @Column("from_id_")
    private String fromId;

    /**
     * 用户类型，用于标记用户是什么系统的用户，默认是 system 类型，表示是系统内部用户
     */
    @Column("type_")
    private String type;

    @Column("password_")
    private String password;

    @Column("nickname_")
    private String nickname;

    @Column("state_")
    private UserState state = UserState.NORMAL;

    @Version
    @Column("version_")
    private Integer version;

    /**
     * 创建新用户
     *
     * @param id     用户 ID
     * @param action 创建指令
     */
    public User(String id, UserCreateAction action) {
        this.id = id;
        this.login = action.getLogin();
        this.nickname = action.getNickname();
        super.andEvent(new UserCreatedEvent(id));
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


}
