package com.eleven.doney.domain;

import com.eleven.core.domain.AbstractAuditDomain;
import com.eleven.core.domain.Deletable;
import com.eleven.core.time.TimeContext;
import com.eleven.doney.model.ProjectSaveAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table("doney_project")
@Getter
@FieldNameConstants
@RequiredArgsConstructor
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Project extends AbstractAuditDomain implements Serializable, Deletable {

    @Id
    @Column("id")
    private final String id;

    @InsertOnlyProperty
    @Column("code")
    private String code;

    @Version
    @Column("version")
    private Integer version;

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("state")
    private String state;

    @Column("url")
    private String url;

    @Column("delete_at")
    private LocalDateTime deleteAt;

    public void initial(ProjectSaveAction action) {
        this.code = action.getCode();
        this.update(action);
    }

    public void update(ProjectSaveAction action) {
        this.title = action.getTitle();
        this.description = action.getDescription();
        this.state = action.getState();
        this.url = action.getUrl();
    }

    @Override
    public void delete() {
        this.deleteAt = TimeContext.localDateTime();
    }

}
