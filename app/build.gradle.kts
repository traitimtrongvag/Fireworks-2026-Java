plugins {
    application
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jline:jline:3.25.0")
    implementation("org.jline:jline-terminal-jansi:3.25.0")
    implementation("org.fusesource.jansi:jansi:2.4.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "org.example.App"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
