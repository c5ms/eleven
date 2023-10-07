package com.eleven.doney.domain;

import com.eleven.core.domain.AbstractAuditableDomain;
import com.eleven.doney.dto.IssueSaveAction;
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
public class Issue extends AbstractAuditableDomain implements Serializable {

    //  "id": 1,
    //        "project": {
    //            "id": 1,
    //            "name": "redmine中文网测试"
    //        },
    //        "tracker": {
    //            "id": 2,
    //            "name": "功能"
    //        },
    //        "status": {
    //            "id": 1,
    //            "name": "新建"
    //        },
    //        "priority": {
    //            "id": 4,
    //            "name": "紧急"
    //        },
    //        "author": {
    //            "id": 1,
    //            "name": "redmine Admin"
    //        },
    //        "assigned_to": {
    //            "id": 1,
    //            "name": "redmine Admin"
    //        },
    //        "category": {
    //            "id": 1,
    //            "name": "啊啊啊"
    //        },
    //        "subject": "test issue",
    //        "description": "<p>aaaaa<br/></p>",
    //        "start_date": "2019-07-21",
    //        "due_date": "2019-07-21",
    //        "done_ratio": 60,
    //        "is_private": false,
    //        "estimated_hours": 5,
    //        "total_estimated_hours": 5,
    //        "spent_hours": 5,
    //        "total_spent_hours": 5,
    //        "created_on": "2019-07-20T16:33:33Z",
    //        "updated_on": "2023-10-01T09:38:46Z",
    //        "closed_on": null
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
