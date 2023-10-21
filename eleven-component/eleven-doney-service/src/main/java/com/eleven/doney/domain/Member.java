package com.eleven.doney.domain;

import com.eleven.core.domain.AbstractAuditDomain;
import com.eleven.doney.model.MemberSaveAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table("doney_member")
@Getter
@FieldNameConstants
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Member extends AbstractAuditDomain implements Serializable {

    @Id
    @Column("id")
    private final String id;

    @Column("user_id")
    private String userId;

    @Column("project_id")
    private String projectId;

    @MappedCollection(idColumn = "member_id", keyColumn = "key")
    private List<MemberRole> roles = new ArrayList<>();

    public Member(String id, String projectId, MemberSaveAction action) {
        this.id = id;
        this.projectId = projectId;
        this.userId = action.getUserId();
        this.update(action);
    }

    public void update(MemberSaveAction action) {

        int key = 1;
        this.roles.clear();
        for (String roleName : action.getRoles()) {
            var role = new MemberRole(this, key, roleName);
            this.roles.add(role);
        }


    }

}
