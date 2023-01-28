
package com.demcia.eleven.cms.domain.entity;

import com.demcia.eleven.core.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;

@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "cms_content_body")
public class CmsContentBody extends BaseEntity {

    @Id
    @Column(name = "id_", nullable = false, length = 100)
    private String id;

    @Lob
    @Column(name = "text_")
    private String text;

    public CmsContentBody() {
    }

    public CmsContentBody(String text) {
        this.text = text;
    }
}