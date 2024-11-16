package com.eleven.core.domain.identity.support;


import cn.hutool.core.lang.Snowflake;
import com.eleven.core.domain.identity.IdentityGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SnowflakeIdentityGenerator implements IdentityGenerator {

    private final Snowflake snowflake;

    @Override
    public String next() {
        return snowflake.nextIdStr();
    }
}
