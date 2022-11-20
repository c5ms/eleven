package com.eleven.upms;

import com.eleven.core.test.arch.ArchTestCase;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchTest {
    private final String packageName = this.getClass().getPackageName();

    @Test
    public void layerCheck() {
        ArchTestCase archTestCase=new ArchTestCase(packageName);
        archTestCase.checkAll();
    }

}
