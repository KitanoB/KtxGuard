plugins {
    kotlin("jvm") version "1.9.10"
    id("io.ktor.plugin") version "2.3.4"
    id("org.jetbrains.kotlinx.kover") version "0.7.4"
}

val ktor_version: String by project
val logback_version: String by project
val koin_version: String by project

group = "com.kitano"
version = "0.0.1"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.kitano.cli.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

dependencies {
    implementation(files("../libs/KtxCryptoCore-0.0.1.jar"))
    implementation("commons-cli:commons-cli:1.5.0")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.10")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
}


kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}