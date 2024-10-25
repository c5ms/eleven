package com.eleven.core.data;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Value
@Builder
@FieldNameConstants
public class Audition {

    @CreatedBy
    @Column("create_by")
    String createBy;

    @CreatedDate
    @Column("create_at")
    LocalDateTime createAt;

    @LastModifiedBy
    @Column("update_by")
    String updateBy;

    @LastModifiedDate
    @Column("update_at")
    LocalDateTime updateAt;

    public static Audition empty() {
        return Audition.builder().build();
    }

}
