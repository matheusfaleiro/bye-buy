pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }

        mavenCentral()

        gradlePluginPortal()
    }
}

/**
 * Configures the Foojay Resolver Convention plugin.
 *
 * This plugin integrates with Gradle's toolchain management system to automatically discover
 * and download appropriate Java Development Kit (JDK) distributions from Foojay's API service.
 *
 * Benefits:
 * - Eliminates the need to manually install JDKs on development/CI environments
 * - Ensures consistent Java toolchain usage across all developers and build environments
 * - Seamlessly resolves the correct JDK version based on project requirements
 * - Supports various JDK distributions via the Foojay API (Friends of OpenJDK)
 *
 * When Gradle needs a specific Java toolchain version, this plugin will automatically
 * discover and provision the appropriate JDK distribution.
 */
plugins {
    id(id = "org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
    }
}

/**
 * Sets the name of the root project.
 */
rootProject.name = "bye-buy"

/**
 * Enables the feature preview for type-safe project accessors in Gradle.
 *
 * Type-safe project accessors provide a type-safe and refactoring-friendly way to access subprojects.
 * This feature is currently in the preview stage and its behavior may change in future Gradle versions.
 */
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
