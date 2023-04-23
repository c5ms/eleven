package com.eleven.core.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.Instant;


@Data
public class AuditMetadata {

    @CreatedBy
    @Column("create_by")
    private String createBy;

    @LastModifiedBy
    @Column("update_by")
    private String updateBy;

    @CreatedDate
    @Column("create_date")
    private Instant createDate;

    @LastModifiedDate
    @Column("update_date")
    private Instant updateDate;

}
