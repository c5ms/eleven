package com.eleven.doney.domain;

import com.eleven.core.domain.AbstractAuditDomain;
import com.eleven.doney.model.IssueSaveAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table("doney_issue")
@Getter
@FieldNameConstants
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Issue extends AbstractAuditDomain implements Serializable {

    @Id
    @Column("id")
    private final String id;

    @Version
    @Column("version")
    private Integer version;

    @Column("project_id")
    private String projectId;

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("state")
    private String state;

    @Column("last_handle_time")
    private LocalDateTime lastHandleTime;

    public Issue(String id, IssueSaveAction action) {
        this.id = id;
        this.update(action);
        this.markNew();
    }

    public void update(IssueSaveAction action) {
        this.title = action.getTitle();
        this.description = action.getDescription();
        this.state = action.getState();
        this.projectId = action.getProjectId();
        this.markOld();
    }
}
