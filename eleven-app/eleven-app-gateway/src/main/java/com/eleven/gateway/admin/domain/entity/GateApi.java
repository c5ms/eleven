package com.eleven.gateway.admin.domain.entity;

import com.cnetong.common.persist.jpa.convert.StringListConverter;
import com.eleven.gateway.core.GatewayRouteProxyMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Entity
@FieldNameConstants
@Schema(description = "应用接口")
@Table(name = "gate_api")
public class GateApi extends AbstractPublishableDomain {

    @Schema(description = "接口名称")
    @Column(name = "name_", length = 100)
    private String name;

    @Lob
    @Schema(description = "描述")
    @Column(name = "description_")
    private String description;

    @Schema(description = "路由模式")
    @Column(name = "proxy_mode_")
    private GatewayRouteProxyMode proxyMode = GatewayRouteProxyMode.staticProxy;

    @Schema(description = "排序")
    @Column(name = "order_")
    private Integer order;

    @Schema(description = "分组")
    @Column(name = "group_")
    private String group;

    @Schema(description = "详情链接")
    @Column(name = "detail_link_")
    private String detailLink;

    @Size(max = 100, message = "绑定域名最多 200 个字符")
    @Schema(description = "绑定域名")
    @Column(name = "host_", length = 200)
    private String host;

    @Schema(description = "需授权访问")
    @Column(name = "secure_")
    private Boolean secure;

    @Size(max = 100, message = "请求方法最多 100 个字符")
    @Schema(description = "请求方法")
    @Column(name = "method_", length = 100)
    private String method;

    @Size(max = 100, message = "请求路径最多 100 个字符")
    @Schema(description = "请求路径")
    @Column(name = "path_", length = 100)
    private String path;

    @Schema(description = "目标服务")
    @Column(name = "service_id", length = 200)
    private String serviceId;

    @Schema(description = "目标服务根路径")
    @Column(name = "service_path", length = 200)
    private String servicePath;

    @Schema(description = "代理的目标 URI ")
    @Convert(converter = StringListConverter.class)
    @Column(name = "uri_", length = 2000)
    private List<String> uris;


    @Schema(description = "访问次数")
    @Column(name = "stat_request_count_", updatable = false)
    private Integer statRequestCount = 0;

    public void update(GateApi api) {
        BeanUtils.copyProperties(api, this);
    }
}
