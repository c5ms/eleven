package com.eleven.upms.domain;

import com.eleven.core.domain.AbstractDomain;
import com.eleven.upms.domain.event.UserGrantedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("upms_user_authority")
@Getter
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class UserAuthority extends AbstractDomain<UserAuthority> {

    @Id
    @Column("id_")
    private String id;

    @Column("user_id_")
    private String userId;

    @Column("name_")
    private String name;

    @Column("type_")
    private String type;

    @Version
    @Column("_version")
    private Integer version;

    public UserAuthority(String id, User user, Authority authority) {
        this.id = id;
        this.userId = user.getId();
        this.name = authority.name();
        this.type = authority.type();
        super.andEvent(new UserGrantedEvent(user.getId(), authority));
        super.markNew();
    }

//    /**
//     * 更新权限
//     *
//     * @param authority 更新的权限
//     */
//    public void update(Authority authority) {
//
//    }
}
