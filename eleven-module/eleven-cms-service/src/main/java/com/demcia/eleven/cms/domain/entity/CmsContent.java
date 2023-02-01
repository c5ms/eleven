package com.demcia.eleven.cms.domain.entity;

import com.demcia.eleven.cms.core.enums.CmsContentState;
import com.demcia.eleven.cms.domain.event.CmsContentPublishEvent;
import com.demcia.eleven.core.domain.entity.BaseSortableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.LocalDateTime;

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
     * 标题
     */
    @Embedded
    private CmsContentTitle title;

    /**
     * 摘要
     */
    @Column(name = "summary_", length = 1000)
    private String summary;

    /**
     * 关键词
     */
    @Column(name = "keywords_", length = 200)
    private String keywords;

    /**
     * 作者
     */
    @Column(name = "author_", length = 150)
    private String author;

    /**
     * 来源
     */
    @Column(name = "source_", length = 150)
    private String source;

    /**
     * 发布时间
     */
    @Column(name = "publish_time_")
    private LocalDateTime publishTime;

    /**
     * 外部链接
     */
    @Column(name = "out_link_", length = 500)
    private String outLink;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "state_", length = 11)
    private CmsContentState state = CmsContentState.DRAFT;

    /**
     * 浏览量
     */
    @Column(name = "views_", length = 11)
    private int views = 0;

    /**
     * 是否置顶
     **/
    @Column(name = "top_", length = 11)
    private boolean top = false;

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
     * 内容扩展
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "ext_id_", nullable = false)
    private CmsContentExt ext;

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

    /**
     * 设置内容的栏目
     *
     * @param channel 栏目
     * @return this
     */
    public CmsContent setChannel(CmsChannel channel) {
        this.channel = channel;
        this.setChannelId(channel.getId());
        return this;
    }

    /**
     * 读取栏目 ID
     *
     * @return 栏目 ID
     */
    public String getChannelId() {
        if (null != this.channelId) {
            return this.channelId;
        }
        if (null != this.channel) {
            return this.channel.getId();
        }
        return null;
    }


    /**
     * 提交内容
     */
    public void publish() {
        if (getState() == CmsContentState.CANCELLED) {
            setState(CmsContentState.PUBLISHED);
        } else {
            setState(CmsContentState.APPROVING);
        }
        registerEvent(new CmsContentPublishEvent(this));
    }
}
