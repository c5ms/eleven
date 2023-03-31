
package com.demcia.eleven.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import jakarta.persistence.MappedSuperclass;

@Getter
@Setter(AccessLevel.PACKAGE)
@FieldNameConstants
@MappedSuperclass
public abstract class BaseSortableEntity extends BaseAuditableEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 排序值
     */
    private int sortIndex = 1;

}
