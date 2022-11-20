package com.eleven.hotel;

import com.eleven.core.test.arch.ArchTestCase;
import org.junit.jupiter.api.Test;

public class ArchTest {
    private final String packageName = this.getClass().getPackageName();

    @Test
    public void layerCheck() {
        ArchTestCase archTestCase = new ArchTestCase(packageName);
        archTestCase.checkAll();
    }

}
