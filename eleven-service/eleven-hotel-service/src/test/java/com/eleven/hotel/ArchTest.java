package com.eleven.hotel;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchTest {
    private final String packageName = this.getClass().getPackageName();

    @Test
    public void domainLayerCheck() {
        var importedClasses = new ClassFileImporter().importPackages(packageName);
        Architectures.LayeredArchitecture applicationArchitecture = layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer("application").definedBy("com.eleven.hotel.application..")
            .layer("endpoint").definedBy("com.eleven.hotel.endpoint..")
            .layer("domain").definedBy("com.eleven.hotel.domain..")

            .whereLayer("application").mayOnlyAccessLayers("domain")
            .whereLayer("endpoint").mayOnlyAccessLayers("application", "domain")
            .whereLayer("domain").mayNotAccessAnyLayer()

            .as("domain layer rule");

        applicationArchitecture.check(importedClasses);


        applicationArchitecture = layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer("domain.hotel").definedBy("com.eleven.hotel.domain.model.hotel..")
            .layer("domain.plan").definedBy("com.eleven.hotel.domain.model.plan..")
            .layer("domain.booking").definedBy("com.eleven.hotel.domain.model.booking..")

            .whereLayer("domain.hotel").mayNotAccessAnyLayer()
            .whereLayer("domain.plan").mayNotAccessAnyLayer()
            .whereLayer("domain.booking").mayNotAccessAnyLayer()

            .as("domain layer rule");

        applicationArchitecture.check(importedClasses);
    }


}
