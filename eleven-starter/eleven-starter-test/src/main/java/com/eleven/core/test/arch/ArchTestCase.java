package com.eleven.core.test.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import lombok.RequiredArgsConstructor;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@RequiredArgsConstructor
public class ArchTestCase {

    private final String packageName;
    private final String domainPackageName;
    private final String apiPackageName;
    private final String endpointPackageName;
    private final String applicationPackageName;
    private final JavaClasses importedClasses;

    public ArchTestCase(String packageName) {
        this.packageName = packageName;
        this.apiPackageName = getLayerPackage("api");
        this.applicationPackageName = getLayerPackage("application");
        this.domainPackageName = getLayerPackage("domain");
        this.endpointPackageName = getLayerPackage("endpoint");
        this.importedClasses = new ClassFileImporter().importPackages(packageName);
    }

    public void checkAll() {
        this.layerCheck();
        this.dependenciesCheck();
        this.containmentCheck();
    }



    public void layerCheck() {
        Architectures.LayeredArchitecture applicationArchitecture = layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer("Api").definedBy(anyPackageFor(apiPackageName))
            .layer("Domain").definedBy(anyPackageFor(domainPackageName))
            .layer("Endpoint").definedBy(anyPackageFor(endpointPackageName))
            .layer("Application").definedBy(anyPackageFor(applicationPackageName))

            .whereLayer("Endpoint").mayOnlyAccessLayers("Application", "Api")
            .whereLayer("Application").mayOnlyAccessLayers("Domain", "Api")
            .whereLayer("Domain").mayOnlyAccessLayers("Api")

            .as("domain driven design layer rule");

        applicationArchitecture.check(importedClasses);
    }

    public void containmentCheck() {
        classes().that().haveSimpleNameEndingWith("Service")
            .should().resideInAPackage(anyPackageFor(applicationPackageName))
            .check(importedClasses);

        classes().that().haveSimpleNameEndingWith("Convertor")
            .should().resideInAPackage(anyPackageFor(applicationPackageName))
            .check(importedClasses);

        classes().that().haveSimpleNameEndingWith("Manager")
            .should().resideInAPackage(anyPackageFor(domainPackageName))
            .check(importedClasses);

        classes().that().haveSimpleNameEndingWith("Repository")
            .should().resideInAPackage(anyPackageFor(domainPackageName))
            .check(importedClasses);
    }


    public void dependenciesCheck() {
        noClasses().that().resideInAPackage(anyPackageFor(domainPackageName))
            .should().dependOnClassesThat().resideInAPackage(anyPackageFor(applicationPackageName))
            .check(importedClasses);

        noClasses().that().resideInAPackage(anyPackageFor(domainPackageName))
            .should().dependOnClassesThat().resideInAPackage(anyPackageFor(endpointPackageName))
            .check(importedClasses);

        noClasses().that().haveNameMatching(anyPackageFor(endpointPackageName) + "*Api")
            .should().dependOnClassesThat().haveNameMatching(anyPackageFor(endpointPackageName) + "*Api")
            .allowEmptyShould(true).check(importedClasses);

        noClasses().that().haveNameMatching(anyPackageFor(applicationPackageName) + "*Service")
            .should().dependOnClassesThat().haveNameMatching(anyPackageFor(applicationPackageName) + "*Service")
            .allowEmptyShould(true).check(importedClasses);

        noClasses().that().haveNameMatching(anyPackageFor(applicationPackageName) + "*Convertor")
            .should().dependOnClassesThat().haveNameMatching(anyPackageFor(applicationPackageName) + "*Convertor")
            .andShould().dependOnClassesThat().haveNameMatching(anyPackageFor(applicationPackageName) + "*Service")
            .andShould().dependOnClassesThat().haveNameMatching(anyPackageFor(endpointPackageName) + "*Api")
            .allowEmptyShould(true).check(importedClasses);

        noClasses().that().haveNameMatching(anyPackageFor(domainPackageName) + "*Manager")
            .should().dependOnClassesThat().haveNameMatching(anyPackageFor(domainPackageName) + "*Manager")
            .andShould().dependOnClassesThat().haveNameMatching(anyPackageFor(endpointPackageName) + "*Api")
            .andShould().dependOnClassesThat().haveNameMatching(anyPackageFor(applicationPackageName) + "*Service")
            .andShould().dependOnClassesThat().haveNameMatching(anyPackageFor(applicationPackageName) + "*Convertor")
            .allowEmptyShould(true).check(importedClasses);
    }

    private String getLayerPackage(String layer) {
        return packageName + "." + layer;
    }

    private String anyPackageFor(String layer) {
        return layer + "..";
    }
}
