package com.eleven.gateway.management.domain;

import com.eleven.gateway.core.GatewayRouteProxyMode;
import com.eleven.gateway.management.model.RouteCreateAction;
import com.eleven.core.data.support.ListValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.List;


@Table("gate_route")
@Getter
@FieldNameConstants
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Route extends AbstractPublishableEntity implements Serializable {

    @Id
    @Column("id")
    private final String id;

    private String name;

    private String description;

    private String stackId;

    private String path;

    private GatewayRouteProxyMode proxyMode;

    private Integer sort;

    private String host;

    private String method;

    private String contentType;

    private String serviceId;

    private String servicePath;

    private String resourceId;

    private String resourcePath;

    private List<String> uris;

    private List<String> filters;

    private List<String> predicates;


    public Route(String id, RouteCreateAction action) {
        this.id = id;
        this.name = action.getName();
        this.description = action.getDescription();
        this.stackId = action.getStackId();
        this.path = action.getPath();
        this.proxyMode = action.getProxyMode();
        this.sort = action.getSort();
        this.host = action.getHost();
        this.method = action.getMethod();
        this.contentType = action.getContentType();
        this.serviceId = action.getServiceId();
        this.servicePath = action.getServicePath();
        this.resourceId = action.getResourceId();
        this.resourcePath = action.getResourcePath();

        this.uris = action.getUris();
        this.filters = action.getFilters();
        this.predicates = action.getPredicates();


//        this.uris = ListValue.of(action.getUris());
//        this.filters = ListValue.of(action.getFilters());
//        this.predicates = ListValue.of(action.getPredicates());
    }
}
