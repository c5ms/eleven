package com.demcia.eleven.domain.upms.entity;

import com.demcia.eleven.core.entity.BaseEntity;
import com.demcia.eleven.domain.upms.enums.UserState;
import com.demcia.eleven.domain.upms.events.UserCreatedEvent;
import com.demcia.eleven.domain.upms.events.UserUpdateEvent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "upms_user")
public class User extends BaseEntity<User> {

    public static final String USER_TYPE_SYSTEM = "sys_inner";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 50)
    private String id;

    @Column(name = "username_", length = 100)
    private String username;

    @Column(name = "password_", length = 100)
    private String password;

    @Column(name = "display_name_", length = 100)
    private String displayName;

    /**
     * 如果用户类型不是 system，表示上一个外部系统用户，则会有对应的外部系统用户 ID
     */
    @Column(name = "from_id_", length = 100, unique = true)
    private String fromId;

    /**
     * 用户类型，用于标记用户是什么系统的用户，默认是 system 类型，表示是系统内部用户
     */
    @Column(name = "type_", length = 50, updatable = false)
    private String type = USER_TYPE_SYSTEM;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_", nullable = false, length = 100)
    private UserState state = UserState.NORMAL;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id_"))
//    @Column(name = "role_code_", length = 50)
//    private Set<String> roles = new HashSet<>();

    @PostPersist
    public void  onCreate() {
        this.andEvent(new UserCreatedEvent());
    }

    @PostUpdate
    public void  onUpdate() {
        this.andEvent(new UserUpdateEvent());
    }
}
