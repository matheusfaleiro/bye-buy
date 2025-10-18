import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.android.application) apply false

    alias(libs.plugins.kotlin.android) apply false

    alias(libs.plugins.kotlin.compose) apply false

    alias(libs.plugins.detekt) apply true
}

/**
 * Configures Detekt static code analysis for all subprojects.
 *
 * Detekt is a static code analysis tool for the Kotlin programming language.
 * This configuration applies the Detekt plugin to all subprojects that have it,
 * enabling consistent code analysis across the entire project.
 *
 * **Key Configurations:**
 * - **basePath**: Sets the base path for file paths in the formatted reports.
 *   If not set, all file paths reported will be absolute.
 * - **source**: Defines the directories that Detekt will scan for Kotlin files.
 *   This is set to the project directory to include all relevant source files.
 * - **parallel**: Enables parallel processing, allowing Detekt to build the abstract syntax tree (AST)
 *   in parallel, which can lead to performance improvements in larger projects.
 * - **config**: Specifies the path to the Detekt configuration file(s).
 * - **buildUponDefaultConfig**: If set to `false`, Detekt's default config file will not be applied
 *   on top of the provided config files. This allows for a fully customized rule set.
 * - **autoCorrect**: If set to `true`, Detekt will attempt to automatically correct some rule violations.
 * - **allRules**: If set to `false`, not all rules are active by default. Only the rules specified
 *   in the configuration file will be applied.
 * - **disableDefaultRuleSets**: If set to `false`, the default Detekt rule sets are used in addition
 *   to any custom rules.
 * - **debug**: If set to `false`, Detekt will not output debug information during task execution.
 * - **ignoreFailures**: If set to `false`, the build will fail if any issues are found that exceed
 *   the specified thresholds.
 * - **ignoredBuildTypes**, **ignoredFlavors**, **ignoredVariants**: Lists of Android build types,
 *   flavors, and variants for which Detekt tasks should not be created. This allows you to skip
 *   analysis for certain builds.
 *
 * **Detekt Plugins Added:**
 * - **detekt-formatting**: Provides additional formatting rules for code style consistency.
 * - **detekt-rules-compose**: Offers rules specific to Jetpack Compose, ensuring best practices
 *   are followed in Compose code.
 *
 * The Detekt tasks are configured to not generate any reports by default (`html`, `xml`, and `txt`
 * reports are disabled).
 * You can enable them as needed for your project.
 */
subprojects {
    plugins.withType<io.gitlab.arturbosch.detekt.DetektPlugin> {
        apply(plugin = "io.gitlab.arturbosch.detekt")

        detekt {
            basePath = projectDir.absolutePath

            source.setFrom(files(projectDir))

            parallel = true

            config.setFrom("$rootDir/config/detekt/detekt.yml")

            buildUponDefaultConfig = false

            autoCorrect = true

            allRules = false

            disableDefaultRuleSets = false

            debug = false

            ignoreFailures = false

            ignoredBuildTypes = listOf("release", "staging", "qa")

            ignoredFlavors = emptyList()

            ignoredVariants = emptyList()
        }

        tasks.withType<Detekt>().configureEach {
            reports {
                html.required.set(false)
                xml.required.set(false)
                txt.required.set(false)
            }
        }

        dependencies {
            detektPlugins(libs.detekt.formatting)
            detektPlugins(libs.detekt.rules.compose)
        }
    }
}

dependencies {
    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.detekt.rules.compose)
}