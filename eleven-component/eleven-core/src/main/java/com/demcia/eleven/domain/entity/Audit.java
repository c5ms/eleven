package com.demcia.eleven.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;


@Getter
@Setter
public class Audit {

    @CreatedBy
    @Column("_create_by")
    private String createBy;

    @LastModifiedBy
    @Column("_update_by")
    private String updateBy;

    @CreatedDate
    @Column("_create_date")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column("_update_date")
    private LocalDateTime updateDate;

    public Audit(String createBy, String updateBy, LocalDateTime createDate, LocalDateTime updateDate) {
        this.createBy = createBy;
        this.updateBy = updateBy;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
