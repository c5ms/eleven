package com.eleven.core.domain.support;

import cn.hutool.core.util.IdUtil;
import com.eleven.core.domain.IdentityGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObjectIdGenerator implements IdentityGenerator {

    @Override
    public synchronized String next() {
        return IdUtil.objectId();
    }

}
