plugins {
    kotlin("jvm") version "1.9.23"
  
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jfree:jfreechart:1.5.3")
    
    // Selenium dependency
    implementation("org.seleniumhq.selenium:selenium-java:4.22.0")
    // WebDriverManager dependency to handle WebDriver binaries automatically
    implementation("io.github.bonigarcia:webdrivermanager:5.8.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}