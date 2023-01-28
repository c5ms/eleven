package com.demcia.eleven.cms.domain.entity;

import com.demcia.eleven.core.domain.entity.BaseSortableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;

@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "cms_content")
public class CmsContent extends BaseSortableEntity {

    @Id
    @Column(name = "id_", nullable = false, length = 100)
    private String id;

    /**
     * 内容标题
     */
    @Column(name = "title", nullable = false, length = 150)
    private String title;

    /**
     * 内容标题是否加粗
     */
    @Column(name = "title_is_bold_")
    private boolean titleIsBold = false;

    /**
     * 内容标题的颜色
     */
    @Column(name = "title_color_", length = 50)
    private String titleColor;

    /**
     * 简短标题
     */
    @Column(name = "short_title_", length = 150)
    private String shortTitle;

    /**
     * 内容状态(1:草稿; 2:初稿; 3:流转中; 4:已审核; 5:已发布; 6:退回; 7:下线; 8:归档; 9:暂存;10:驳回 )
     */
    private int status=1;

    /**
     * 浏览量
     */
    @Column(name = "views_", length = 11)
    private int views = 0;

    /**
     * 是否置顶
     **/
    @Column(name = "top_", length = 11)
    private boolean top;

    /**
     * 是否加入回收站
     **/
    @Column(name = "recycle_")
    private boolean recycle = false;

    /**
     * 内容正文
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "body_id_", nullable = false)
    private CmsContentBody body;

    /**
     * 栏目
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id_")
    private CmsChannel channel;

    /**
     * 栏目 ID
     */
    @Column(name = "channel_id_", updatable = false, insertable = false)
    private String channelId;

}
