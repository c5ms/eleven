package com.eleven.core.domain.support;

import com.eleven.core.domain.BeanConvertor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class ModelMapperBeanConvertor implements BeanConvertor {
    private final ModelMapper modelMapper;

    @Override
    public <T> T to(Object source, Class<T> targetClass) {
        return modelMapper.map(source,targetClass);
    }
}
