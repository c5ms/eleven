package com.demcia.eleven.domain.entity;

import com.demcia.eleven.domain.entity.listener.BaseAuditableEntityListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Getter
@Setter(AccessLevel.PACKAGE)
@FieldNameConstants
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class, BaseAuditableEntityListener.class})
public abstract class BaseAuditableEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "_create_by", updatable = false, length = 100)
    private String createBy;

    @LastModifiedBy
    @Column(name = "_update_by", length = 100)
    private String updateBy;

    @CreatedDate
    @Column(name = "_create_date", updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "_update_date")
    private LocalDateTime updateDate;

    @Version
    @Column(name = "_version", nullable = false)
    private Integer version;

    @Column(name = "_is_reserved")
    private boolean isReserved = false;

//    @Column(name = "_is_deleted")
//    private boolean isDeleted = false;



}
