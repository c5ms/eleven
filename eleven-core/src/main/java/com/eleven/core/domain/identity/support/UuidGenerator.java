package com.eleven.core.domain.identity.support;

import cn.hutool.core.util.IdUtil;
import com.eleven.core.domain.identity.IdentityGenerator;

public class UuidGenerator implements IdentityGenerator {

    @Override
    public String next() {
        return IdUtil.simpleUUID();
    }

}
