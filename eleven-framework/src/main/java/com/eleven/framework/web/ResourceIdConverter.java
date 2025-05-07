package com.eleven.framework.web;

import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ResourceIdConverter implements Converter<String, ResourceId> {

    @Override
    public ResourceId convert(@Nonnull String original) {
        return new ResourceId(original);
    }
}
