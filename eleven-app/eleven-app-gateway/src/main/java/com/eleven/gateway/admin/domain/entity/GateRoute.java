package com.eleven.gateway.admin.domain.entity;

import com.cnetong.common.persist.jpa.convert.StringListConverter;
import com.eleven.gateway.core.GatewayRouteProxyMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "gate_route")
public class GateRoute extends AbstractPublishableDomain {

    @Size(max = 100, message = "路由名最多 100 个字符")
    @NotBlank(message = "路由名不可为空")
    @Schema(description = "路由名")
    @Column(name = "name_", length = 100)
    private String name;

    @Size(max = 200, message = "描述最多 200 个字符")
    @Schema(description = "描述")
    @Column(name = "description_", length = 200)
    private String description;

    @Size(max = 100, message = "路由所属站点 ID 最多 100 个字符")
    @Schema(description = "站点")
    @Column(name = "stack_id_")
    private String stackId;

    @Size(max = 100, message = "网关路径最多 100 个字符")
    @Schema(description = "网关路径")
    @Column(name = "path_", length = 200)
    private String path;

    @Schema(description = "路由模式")
    @Column(name = "proxy_mode_")
    private GatewayRouteProxyMode proxyMode = GatewayRouteProxyMode.staticProxy;

    @Schema(description = "代理的目标 URI ")
    @Convert(converter = StringListConverter.class)
    @Column(name = "uri_", length = 2000)
    private List<String> uris;

    @Schema(description = "排序")
    @Column(name = "order_")
    private Integer order;

    @Size(max = 100, message = "绑定域名最多 100 个字符")
    @Schema(description = "绑定域名")
    @Column(name = "host_", length = 200)
    private String host;

    @Size(max = 100, message = "请求方法最多 100 个字符")
    @Schema(description = "请求方法")
    @Column(name = "method_", length = 100)
    private String method;

    @Size(max = 200, message = "内容类型最多 200 个字符")
    @Schema(description = "内容类型")
    @Column(name = "content_type_", length = 200)
    private String contentType;

    @Schema(description = "目标服务")
    @Column(name = "service_id", length = 200)
    private String serviceId;

    @Schema(description = "目标服务根路径")
    @Column(name = "service_path", length = 200)
    private String servicePath;

    @Schema(description = "静态资源")
    @Column(name = "resource_id_", length = 200)
    private String resourceId;

    @Schema(description = "静态资源根路径")
    @Column(name = "resource_path", length = 200)
    private String resourcePath;

    @Schema(description = "请求过滤器")
    @Convert(converter = StringListConverter.class)
    @Column(name = "filters_", length = 2000)
    private List<String> filters;

    @Schema(description = "路由规则")
    @Convert(converter = StringListConverter.class)
    @Column(name = "predicates_", length = 2000)
    private List<String> predicates = new ArrayList<>();

    public void update(GateRoute gateRoute) {
        this.setMethod(gateRoute.getMethod());
        this.setName(gateRoute.getName());
        this.setUris(gateRoute.getUris());
        this.setContentType(gateRoute.getContentType());
        this.setServiceId(gateRoute.getServiceId());
        this.setHost(gateRoute.getHost());
        this.setPath(gateRoute.getPath());
        this.setFilters(gateRoute.getFilters());
        this.setStackId(gateRoute.getStackId());
        this.setPredicates(gateRoute.getPredicates());
        this.setOrder(gateRoute.getOrder());
        this.setProxyMode(gateRoute.getProxyMode());
        this.setResourceId(gateRoute.getResourceId());
        this.setServicePath(gateRoute.getServicePath());
        this.setResourcePath(gateRoute.getResourcePath());
    }


}
