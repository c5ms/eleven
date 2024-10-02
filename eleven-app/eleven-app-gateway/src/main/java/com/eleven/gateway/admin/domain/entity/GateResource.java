package com.eleven.gateway.admin.domain.entity;

import com.cnetong.common.time.TimeContext;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@FieldNameConstants
@Schema(description = "网关静态资源")
@Table(name = "gate_resource")
public class GateResource extends AbstractExceptionableDomain {

    @Schema(description = "资源名称")
    @Column(name = "name_", length = 100)
    private String name;

    @Schema(description = "默认页")
    @Column(name = "index_", length = 200)
    private String index;

    @Schema(description = "缓存天数")
    @Column(name = "cache_days_")
    private Integer cacheDays;

    @Schema(description = "开启 GZIP 压缩")
    @Column(name = "enabled_gzip_")
    private Boolean enabledGzip;

    @Schema(description = "资源描述")
    @Column(name = "description_")
    private String description;

    @Schema(description = "最新上传时间")
    @Column(name = "update_date_")
    private LocalDateTime updateDate;

    @Schema(description = "当前资源包")
    @OneToOne
    @JoinColumn(name = "current_pack_id_")
    private GateResourcePack pack;

    public void update(GateResource gateResource) {
        this.setName(gateResource.getName());
        this.setDescription(gateResource.getDescription());
        this.setCacheDays(gateResource.getCacheDays());
        this.setEnabledGzip(gateResource.getEnabledGzip());
        this.setIndex(gateResource.getIndex());
    }


    public void release(GateResourcePack pack) {
        this.setPack(pack);
        this.setUpdateDate(TimeContext.localDateTime());
        pack.setReleased(true);
    }
}
