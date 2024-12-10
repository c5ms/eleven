package com.eleven.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Embeddable
@NoArgsConstructor
@FieldNameConstants
public class Audition {

    @CreatedBy
    @Column(name = "create_by")
    private String createBy;

    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @LastModifiedBy
    @Column(name = "update_by")
    private String updateBy;

    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updateAt;

}
