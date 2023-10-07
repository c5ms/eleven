package com.eleven.doney.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Table("doney_member_role")
@Getter
@FieldNameConstants
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class MemberRole implements Serializable {

    @Column("member_id")
    private final String memberId;

    @Column("user_id")
    private final String userId;

    @Column("project_id")
    private final String projectId;

    @Column("name")
    private final String name;

    @Column("key")
    private Integer key;

    public MemberRole(Member member, Integer key, String name) {
        this.memberId = member.getId();
        this.userId = member.getUserId();
        this.projectId = member.getProjectId();
        this.key = key;
        this.name = name;
    }

}
