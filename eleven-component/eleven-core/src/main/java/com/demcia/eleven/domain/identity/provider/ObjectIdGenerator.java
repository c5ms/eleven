package com.demcia.eleven.domain.identity.provider;

import cn.hutool.core.util.IdUtil;
import com.demcia.eleven.domain.identity.IdentityGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObjectIdGenerator implements IdentityGenerator {

    @Override
    public synchronized String next() {
        return IdUtil.objectId();
    }

}
