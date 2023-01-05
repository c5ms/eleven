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
@Table(name = "cms_content")
public class CmsContent extends BaseEntity {

    @Id
    @Column(name = "id_", nullable = false, length = 100)
    private String id;

    @Column(name = "title_", length = 100)
    private String title;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "body_id_", nullable = false)
    private CmsContentBody body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id_")
    private CmsChannel channel;


    @Getter
    @Setter
    @Entity
    @FieldNameConstants
    @Table(name = "cms_content_body")
    public static class CmsContentBody extends BaseEntity {

        @Id
        @Column(name = "id_", nullable = false, length = 100)
        private String id;

        @Lob
        @Column(name = "content_")
        private String content;

        public CmsContentBody() {
        }

        public CmsContentBody(String content) {
            this.content = content;
        }
    }
}
