import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.changelog.closure
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun properties(key: String) = project.findProperty(key).toString()

plugins {
	// Java support
	id("java")
	// Kotlin support
	id("org.jetbrains.kotlin.jvm") version "1.4.31"
	// gradle-intellij-plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
	id("org.jetbrains.intellij") version "0.7.2"
	// gradle-changelog-plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
	id("org.jetbrains.changelog") version "1.1.2"
	// detekt linter - read more: https://detekt.github.io/detekt/gradle.html
	id("io.gitlab.arturbosch.detekt") version "1.15.0"
	// ktlint linter - read more: https://github.com/JLLeitschuh/ktlint-gradle
	id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

if (!hasProperty("StudioCompilePath")) {
	throw GradleException("No StudioCompilePath value was set, please create gradle.properties file")
}

// Configure project's dependencies
repositories {
	mavenCentral()
	jcenter()
}

// Configure gradle-intellij-plugin plugin.
// Read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
	pluginName = properties("pluginName")
	version = properties("platformVersion")
	type = properties("platformType")
	downloadSources = properties("platformDownloadSources").toBoolean()
	updateSinceUntilBuild = true

	intellij.localPath = if (project.hasProperty("StudioRunPath")) properties("StudioRunPath") else properties("StudioRunPath")

	// Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
	setPlugins(*properties("platformPlugins").split(',').map(String::trim).filter(String::isNotEmpty).toTypedArray())
}

dependencies {
	detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.15.0")
}

// Configure gradle-changelog-plugin plugin.
// Read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
	version = properties("pluginVersion")
	groups = emptyList()
}

// Configure detekt plugin.
// Read more: https://detekt.github.io/detekt/kotlindsl.html
detekt {
	config = files("./detekt-config.yml")
	buildUponDefaultConfig = true
	ignoreFailures = true // If set to `true` the build does not fail when the maxIssues count was reached. Defaults to `false`.

	reports {
		html.enabled = true // observe findings in your browser with structure and code snippets
		xml.enabled = true // checkstyle like format mainly for integrations like Jenkins
		txt.enabled = true // similar to the console output, contains issue signature to manually edit baseline files
		sarif.enabled = true // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with Github Code Scanning
	}
}

tasks {
	// Set the compatibility versions to 1.8
	withType<JavaCompile> {
		sourceCompatibility = "1.8"
		targetCompatibility = "1.8"
	}
	withType<KotlinCompile> {
		kotlinOptions.jvmTarget = "1.8"
	}

	withType<Detekt> {
		jvmTarget = "1.8"
	}

	patchPluginXml {
		version(properties("pluginVersion"))
		sinceBuild(properties("pluginSinceBuild"))
		untilBuild(properties("pluginUntilBuild"))

		// Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
		pluginDescription(
            closure {
                File("./README.md").readText().lines().run {
                    val start = "<!-- Plugin description -->"
                    val end = "<!-- Plugin description end -->"

                    if (!containsAll(listOf(start, end))) {
                        throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                    }
                    subList(indexOf(start) + 1, indexOf(end))
                }.joinToString("\n").run { markdownToHTML(this) }
            }
		)

		// Get the latest available change notes from the changelog file
		changeNotes(
            closure {
                changelog.getLatest().toHTML()
            }
		)
	}

	runPluginVerifier {
		ideVersions(properties("pluginVerifierIdeVersions"))
	}

	publishPlugin {
		dependsOn("patchChangelog")
		token(System.getenv("PUBLISH_TOKEN"))
		// pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
		// Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
		// https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
		channels(properties("pluginVersion").split('-').getOrElse(1) { "default" }.split('.').first())
	}
}