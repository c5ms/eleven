package com.demcia.eleven.upms.domain;

import com.demcia.eleven.upms.domain.event.UserGrantedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("upms_user_role")
@With
@Getter
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class UserRole extends AbstractAggregateRoot<UserRole> {

    @Id
    @Column("id_")
    private final String id;

    @Column("user_id_")
    private String userId;

    @Column("role_")
    private String role;

    @Version
    @Column("version_")
    private Integer version;

    public UserRole(User user, String role) {
        this.userId = user.getId();
        this.role = role;
        this.id = userId + "@" + role;
        super.andEvent(new UserGrantedEvent(user.getId(), Authority.ofRole(role)));
    }

}
