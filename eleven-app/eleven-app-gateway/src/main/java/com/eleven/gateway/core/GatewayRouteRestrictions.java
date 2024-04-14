package com.eleven.gateway.core;

import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public class GatewayRouteRestrictions {

    Set<String> methods;
    Set<MediaType> contentTypes;

    @Builder
    public GatewayRouteRestrictions(List<String> methods,
                                    List<String> contentTypes) {

        if (null == methods) {
            this.methods = new HashSet<>();
        } else {
            this.methods = methods.stream()
                    .map(StringUtils::trim)
                    .filter(StringUtils::isNotBlank)
                    .map(StringUtils::toRootLowerCase)
                    .collect(Collectors.toSet());
        }

        if (null == contentTypes) {
            this.contentTypes = new HashSet<>();
        } else {
            this.contentTypes = contentTypes.stream()
                    .map(StringUtils::trim)
                    .filter(StringUtils::isNotBlank)
                    .map(MediaType::parseMediaType)
                    .collect(Collectors.toSet());
        }

    }

}
