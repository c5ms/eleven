package com.eleven.gateway.admin.domain.entity;

import com.cnetong.common.domain.AbstractIdDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.sql.Blob;


@Getter
@Setter
@Entity
@Accessors(chain = true)
@FieldNameConstants
@Schema(description = "网关静态资源包内容")
@Table(name = "gate_resource_pack_content")
public class GateResourcePackContent extends AbstractIdDomain {

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Schema(description = "程序包")
    @Column(name = "pack_", nullable = false)
    private Blob body;

}
