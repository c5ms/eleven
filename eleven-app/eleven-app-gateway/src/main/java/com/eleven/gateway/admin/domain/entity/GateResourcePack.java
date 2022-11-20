package com.eleven.gateway.admin.domain.entity;

import com.cnetong.common.domain.AbstractDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Accessors(chain = true)
@FieldNameConstants
@Schema(description = "网关静态资源包")
@Table(name = "gate_resource_pack")
public class GateResourcePack extends AbstractDomain {

    @JsonIgnore
    @Schema(description = "程序包")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "dist_id_")
    private GateResourcePackContent content;

    @Schema(description = "是否已发布")
    @Column(name = "is_released_")
    private boolean isReleased = false;

    @Schema(description = "包文件名")
    @Column(name = "file_name_")
    private String fileName;

    @Schema(description = "所属资源")
    @Column(name = "resource_id_")
    private String resourceId;

}
