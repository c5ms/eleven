package com.eleven.upms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("upms_user")
@With
@Getter
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Role {

    @Id
    @Column("id")
    private String id;

    @Column("login")
    private String name;

    @Version
    @Column("version")
    private String version;

    /**
     * 创建新角色
     *
     * @param id   角色 ID
     * @param name 角色名
     */
    public Role(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
