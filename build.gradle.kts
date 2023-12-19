import java.io.File
import org.gradle.api.provider.Provider
import kotlin.math.ceil



plugins {
    kotlin("jvm") version "1.9.10"
}

group = "com.kitano"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("libs/KtxCryptoCore-0.0.1.jar"))
}


kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
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


