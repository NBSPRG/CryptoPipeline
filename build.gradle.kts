plugins {
    kotlin("jvm") version "1.9.24"
    application
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("dev.openfeature:sdk:+")
    implementation("com.devcycle:java-server-sdk:1.+")

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

sourceSets {
    named("main") {
        kotlin.srcDirs(".")
    }
    named("test") {
        kotlin.srcDirs("test")
    }
}

application {
    mainClass.set("MainKt")
}

tasks.test {
    useJUnitPlatform()
}
