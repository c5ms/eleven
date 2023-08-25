package com.eleven.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;


@Getter
@Setter
@FieldNameConstants
public abstract class AbstractAuditableDomain<T extends AbstractAuditableDomain<T>> extends AbstractDomain<T> {

    @CreatedBy
    @Column(AuditMetadata.col_create_by)
    private String createBy;

    @LastModifiedBy
    @Column(AuditMetadata.col_update_by)
    private String updateBy;

    @CreatedDate
    @Column(AuditMetadata.col_create_at)
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(AuditMetadata.col_update_at)
    private LocalDateTime updateAt;


}
