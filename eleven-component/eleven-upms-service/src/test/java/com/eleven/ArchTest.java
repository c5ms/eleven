package com.eleven;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchTest {
    private static final String packageName = "com.eleven";
    private static final String moduleName = "upms";

    private static final String modulePackageName = getModulePackageName(moduleName);

    @Test
    public void layerCheck() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(modulePackageName);

        Architectures.LayeredArchitecture architecture = layeredArchitecture()
            .consideringAllDependencies()
            .layer("Configure").definedBy(getLayerPackageIdentifiers("configure"))
            .layer("Domain").definedBy(getLayerPackageIdentifiers("domain"))
            .layer("Endpoint").definedBy(getLayerPackageIdentifiers("endpoint"))
            .layer("Support").definedBy(getLayerPackageIdentifiers("support"))

            .whereLayer("Configure").mayNotBeAccessedByAnyLayer()
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Endpoint")
            .whereLayer("Endpoint").mayNotBeAccessedByAnyLayer()
            .whereLayer("Support").mayNotBeAccessedByAnyLayer()

            .as("domain driven design layer rule");

        architecture.evaluate(importedClasses);
    }

    private static String getModulePackageName(String moduleName) {
        return packageName + "." + moduleName;
    }

    private static String getLayerPackageIdentifiers( String layer) {
        return modulePackageName + "." + layer+"..";
    }
}
