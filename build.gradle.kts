import java.io.File
import org.gradle.api.provider.Provider
import kotlin.math.ceil

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project



plugins {
    kotlin("jvm") version "1.9.10"
    id("io.ktor.plugin") version "2.3.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
    id("org.jetbrains.kotlinx.kover") version "0.7.4"
}

group = "com.kitano"
version = "0.0.1"

application {
    mainClass.set("com.kitano.cli.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

repositories {
    mavenCentral()
}


kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("commons-cli:commons-cli:1.5.0")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
}

tasks.register("updateCoverageBadge") {
    doLast {

        val coverageFile: Provider<RegularFile> = layout.buildDirectory.file("kover/coverage.txt")

        if (!coverageFile.isPresent) {
            println("Coverage file not found!")
            return@doLast
        }

        val coverageText: String = coverageFile.get().asFile.readText()

        val matchResult = Regex("""application line coverage: (\d+\.\d+)%""").find(coverageText)

        if (matchResult == null) {
            println("No match found in coverage.txt!")
            return@doLast
        }

        val coveragePercentage = matchResult.groups[1]?.value ?: return@doLast

        val readmeFile =  file("./README.md")

        if (!readmeFile.exists()) {
            println("README.md not found!")
            return@doLast
        }

        var readmeContent = readmeFile.readText()
        val roundedCoveragePercentage = ceil(coveragePercentage.toDouble()).toInt()
        readmeContent = readmeContent.replace(Regex("\\!\\[Coverage]\\(https://img.shields.io/badge/Coverage-\\d+-green\\)"), "![Coverage](https://img.shields.io/badge/Coverage-${roundedCoveragePercentage}-green)")

        readmeFile.writeText(readmeContent)

        println("Code coverage badge updated and README.md updated!")
    }
}


fun String.runCommand(): String {
    return ProcessBuilder(*split(" ").toTypedArray())
        .directory(project.rootDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectErrorStream(true)
        .start()
        .inputStream.bufferedReader().readText().trim()
}

tasks.register("updateKotlinBadge") {
    doLast {
        val projectDir = project.rootDir

        fun countLines(file: File): Int {
            return if (file.isDirectory) {
                file.listFiles()?.sumOf { countLines(it) } ?: 0
            } else {
                file.readLines().size
            }
        }

        val kotlinLines = projectDir.walkTopDown().filter { it.extension == "kt" }.sumOf { countLines(it) }
        val totalLines = projectDir.walkTopDown().filter { it.extension == "kt" || it.extension == "java" }.sumOf { countLines(it) }

        val kotlinPercentage = (kotlinLines.toDouble() / totalLines.toDouble() * 100).toInt()

        val readmeFile = File("${project.rootDir}/README.md")
        if (!readmeFile.exists()) {
            println("Fichier README.md not found!")
            return@doLast
        }

        var readmeContent = readmeFile.readText()
        val roundedKotlinPercentage = ceil(kotlinPercentage.toDouble()).toInt()
        readmeContent = readmeContent.replace(Regex("\\!\\[Kotlin]\\(https://img.shields.io/badge/Kotlin-\\d+-orange\\)"), "![Kotlin](https://img.shields.io/badge/Kotlin-${roundedKotlinPercentage}-orange)")

        readmeFile.writeText(readmeContent)
        println("Badge Kotlin in README.md updated!")
    }
}


