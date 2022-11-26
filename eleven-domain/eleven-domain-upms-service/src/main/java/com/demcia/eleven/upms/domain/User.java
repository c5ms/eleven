package com.demcia.eleven.upms.domain;

import com.demcia.eleven.core.domain.entity.BaseEntity;
import com.demcia.eleven.upms.core.enums.UserState;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "upms_user")
public class User extends BaseEntity {

    public static final String USER_TYPE_SYSTEM = "sys_inner";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_", nullable = false, length = 50)
    private Long id;

    @Column(name = "username_", length = 100,unique = true)
    private String username;

    @Column(name = "password_", length = 100)
    private String password;

    @Column(name = "nickname_", length = 100)
    private String nickname;

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

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "upms_user_role", joinColumns = @JoinColumn(name = "user_id_"))
    @Column(name = "role_id_", length = 50)
    private Set<Long> roles = new HashSet<>();


}
