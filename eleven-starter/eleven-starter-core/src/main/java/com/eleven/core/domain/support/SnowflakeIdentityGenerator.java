package com.eleven.core.domain.support;


import cn.hutool.core.lang.Snowflake;
import com.eleven.core.domain.IdentityGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SnowflakeIdentityGenerator implements IdentityGenerator {

    private final Snowflake snowflake;

    @Override
    public String next() {
        return snowflake.nextIdStr();
    }
}
