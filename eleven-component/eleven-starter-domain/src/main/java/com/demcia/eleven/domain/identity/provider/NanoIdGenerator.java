package com.demcia.eleven.domain.identity.provider;

import cn.hutool.core.util.IdUtil;
import com.demcia.eleven.domain.identity.IdentityGenerator;

public class NanoIdGenerator implements IdentityGenerator {

    @Override
    public String next() {
        return IdUtil.nanoId();
    }

}
