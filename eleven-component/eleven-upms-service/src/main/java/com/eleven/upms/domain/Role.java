package com.eleven.upms.domain;

import com.eleven.core.data.AbstractAuditEntity;
import com.eleven.upms.model.RoleCreateAction;
import com.eleven.upms.model.RoleUpdateAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("upms_role")
@With
@Getter
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Role extends AbstractAuditEntity {

    @Id
    @Column("id")
    private String id;

    @Column("code")
    private String code;

    @Column("name")
    private String name;

    public Role(String id, RoleCreateAction action) {
        this.id = id;
        this.code = action.getCode();
        this.name = action.getName();
    }

    public void update(RoleUpdateAction action) {
        this.name = action.getName();
    }
}
