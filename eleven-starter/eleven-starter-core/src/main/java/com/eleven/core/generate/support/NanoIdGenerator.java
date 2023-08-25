package com.eleven.core.generate.support;

import cn.hutool.core.lang.id.NanoId;
import cn.hutool.core.util.IdUtil;
import com.eleven.core.generate.IdentityGenerator;

public class NanoIdGenerator implements IdentityGenerator {

    private static final char[] DEFAULT_ALPHABET =
            "ABCDEF1234567890".toCharArray();

    @Override
    public String next() {
        return NanoId.randomNanoId(null,DEFAULT_ALPHABET,18);
    }

}
