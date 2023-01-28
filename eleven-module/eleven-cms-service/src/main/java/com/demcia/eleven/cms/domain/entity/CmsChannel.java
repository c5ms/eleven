package com.demcia.eleven.cms.domain.entity;

import com.demcia.eleven.core.domain.entity.BaseAuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "cms_channel")
public class CmsChannel extends BaseAuditableEntity {

    @Id
    @Column(name = "id_", nullable = false, length = 100)
    private String id;

    @Column(name = "title_", length = 100)
    private String title;

    @Column(name = "path_", length = 100)
    private String path;

    @Column(name = "description_", length = 200)
    private String description;

}
