package com.eleven.framework.domain.support;

import cn.hutool.core.util.IdUtil;
import com.eleven.framework.domain.IdentityGenerator;

public class UuidGenerator implements IdentityGenerator {

    @Override
    public String next() {
        return IdUtil.simpleUUID();
    }

}
