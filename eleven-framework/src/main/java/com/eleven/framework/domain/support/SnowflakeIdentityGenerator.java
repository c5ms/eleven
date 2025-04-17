package com.eleven.framework.domain.support;


import cn.hutool.core.lang.Snowflake;
import com.eleven.framework.domain.IdentityGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SnowflakeIdentityGenerator implements IdentityGenerator {

    private final Snowflake snowflake;

    @Override
    public String next() {
        return snowflake.nextIdStr();
    }
}
