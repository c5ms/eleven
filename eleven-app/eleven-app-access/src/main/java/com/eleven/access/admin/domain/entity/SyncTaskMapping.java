package com.eleven.access.admin.domain.entity;

import com.cnetong.common.domain.AbstractIdDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@FieldNameConstants
@Table(name = "sync_task_mapping")
public class SyncTaskMapping extends AbstractIdDomain {
    private static final long serialVersionUID = 1L;

    @Column(name = "collection_")
    private String collection;

    @Column(name = "transformer_")
    private String transformer;

    @Column(name = "source_name_")
    private String sourceName;

    @Column(name = "target_name_")
    private String targetName;

    /**
     * @deprecated 目前在字段映射中的唯一键无用
     */
    @Deprecated
    @Column(name = "unique_")
    private Boolean isUnique;

    @Column(name = "task_id_")
    private String taskId;

}
