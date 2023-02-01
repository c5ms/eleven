package com.demcia.eleven.cms.domain.entity;

import com.demcia.eleven.core.domain.entity.BaseSortableEntity;
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
@Table(name = "cms_content")
public class CmsSite extends BaseSortableEntity {

    @Id
    @Column(name = "id_", nullable = false, length = 100)
    private String id;

}
