package com.demcia.eleven.cms.domain.entity;

import com.demcia.eleven.core.domain.entity.BaseEntity;
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
@Table(name = "cms_content_ext")
public class CmsContentExt extends BaseEntity {

    @Id
    @Column(name = "id_", nullable = false, length = 100)
    private String id;


}
