package com.demcia.eleven.cms.domain.entity;

import com.demcia.eleven.core.domain.entity.BaseAuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "cms_channel")
public class CmsChannel extends BaseAuditableEntity {

    @Id
    @Column(name = "id_", nullable = false, length = 100)
    private String id;

    @Column(name = "title_", length = 100)
    private String title;

    @Column(name = "path_", length = 100)
    private String path;

    @Column(name = "description_", length = 200)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY,  optional = false)
    @JoinColumn(name = "parent_id_", nullable = true)
    private CmsChannel parent;

    @Column(name = "parent_id_", nullable = true, length = 100,updatable = false,insertable = false)
    private String parentId;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @OrderBy("id desc")
    private List<CmsChannel> children = new java.util.ArrayList<>();


    /**
     * 读取父栏目 ID
     *
     * @return 父栏目 ID
     */
    public String getParentId() {
        if (null != this.parentId) {
            return this.parentId;
        }
        if (null != this.parent) {
            return this.parent.getId();
        }
        return null;
    }

    /**
     * 设置父栏目
     *
     * @param parent 父栏目
     * @return this
     */
    public CmsChannel setParent(CmsChannel parent) {
        this.parent = parent;
        this.setParentId(parent.getId());
        return this;
    }



}
