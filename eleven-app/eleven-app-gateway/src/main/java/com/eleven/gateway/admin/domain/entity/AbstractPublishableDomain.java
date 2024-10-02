package com.eleven.gateway.admin.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Setter
@Getter
@MappedSuperclass
@FieldNameConstants
public class AbstractPublishableDomain extends AbstractExceptionableDomain {


    @Schema(description = "发布状态")
    @Column(name = "published_", nullable = false)
    private boolean published = false;

    public void publish() {
        this.setPublished(true);
    }

    public void cancel() {
        this.setPublished(false);
    }

}
