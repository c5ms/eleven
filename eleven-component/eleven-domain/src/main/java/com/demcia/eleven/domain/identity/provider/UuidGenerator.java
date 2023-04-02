package com.demcia.eleven.domain.identity.provider;

import cn.hutool.core.util.IdUtil;
import com.demcia.eleven.domain.identity.IdentityGenerator;

import java.util.UUID;

public class UuidGenerator implements IdentityGenerator {

    @Override
    public String next() {
        return IdUtil.simpleUUID();
    }

}
