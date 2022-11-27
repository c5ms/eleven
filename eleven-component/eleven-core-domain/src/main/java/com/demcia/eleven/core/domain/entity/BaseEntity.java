package com.demcia.eleven.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter(AccessLevel.PACKAGE)
@FieldNameConstants
@MappedSuperclass
@GenericGenerator(name = BaseEntity.ID_GENERATOR,strategy = "com.demcia.eleven.core.domain.entity.id.ElevenIdentifierGenerator")
@EntityListeners({AuditingEntityListener.class, BaseEntityListener.class})
public abstract class BaseEntity implements Serializable, Persistable<String> {
    private static final long serialVersionUID = 1L;

    public static final String ID_GENERATOR="elevenId";


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

    @Transient
    private boolean isNew = true;


    @JsonIgnore
    @Transient
    public boolean isNew() {
        return this.isNew;
    }

    @PostUpdate
    @PostPersist
    @PostLoad
    protected void markNotNew() {
        this.isNew = false;
    }


    /**
     * 设置 ID 的逻辑
     * @param id ID
     */
   public   abstract void setId(String id);

}
