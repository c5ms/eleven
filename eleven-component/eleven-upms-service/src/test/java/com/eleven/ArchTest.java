package com.eleven;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchTest {
    private static final String packageName = "com.eleven.upms";

    @Test
    public void layerCheck() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(packageName);

        Architectures.LayeredArchitecture applicationArchitecture = layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer("Core").definedBy(getApplicationLayerPackageIdentifiers("core"))
            .layer("Configure").definedBy(getApplicationLayerPackageIdentifiers("configure"))
            .layer("Domain").definedBy(getApplicationLayerPackageIdentifiers("domain"))
            .layer("Endpoint").definedBy(getApplicationLayerPackageIdentifiers("endpoint"))
            .layer("Application").definedBy(getApplicationLayerPackageIdentifiers("application"))

            .whereLayer("Endpoint").mayOnlyAccessLayers("Application", "Core")
            .whereLayer("Application").mayOnlyAccessLayers("Domain", "Core")
            .whereLayer("Domain").mayOnlyAccessLayers("Configure", "Core")

            .as("domain driven design layer rule");

        applicationArchitecture.check(importedClasses);
    }


    private static String getApplicationLayerPackageIdentifiers(String layer) {
        return packageName + "." + layer + "..";
    }

}
