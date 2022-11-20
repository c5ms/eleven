package com.eleven.core.domain.support;

import cn.hutool.core.lang.id.NanoId;
import com.eleven.core.domain.IdentityGenerator;

public class NanoIdGenerator implements IdentityGenerator {

    private static final char[] DEFAULT_ALPHABET =
        "ABCDEF1234567890".toCharArray();

    @Override
    public String next() {
        return NanoId.randomNanoId(null, DEFAULT_ALPHABET, 18);
    }

}
