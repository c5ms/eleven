package com.eleven.travel.domain.user;

import com.eleven.framework.data.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("upms_role")
@Getter
@Builder
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Role extends AbstractEntity {

    @Id
    @Column("id")
    private String id;

    @Column("code")
    private String code;

    @Column("name")
    private String name;


    public void update(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
