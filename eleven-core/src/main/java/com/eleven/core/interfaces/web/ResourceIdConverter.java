package com.eleven.core.interfaces.web;

import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ResourceIdConverter implements Converter<String, ResourceId> {

    @Override
    public ResourceId convert(@Nonnull String from) {
        var dig = StringUtils.getDigits(from);
        if (StringUtils.isBlank(dig)) {
            throw Rests.throw404();
        }
        return new ResourceId(Long.parseLong(dig));
    }
}
