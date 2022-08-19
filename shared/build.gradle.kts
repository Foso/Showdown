
plugins {
    kotlin("multiplatform")

    id("kotlinx-serialization")
}

group "com.example"
version "1.0-SNAPSHOT"
repositories {
    google()
    mavenCentral()
}

val kmdcVersion = "0.0.4"

kotlin {
    js(IR) {
        browser {
            testTask {
                testLogging {
                    showExceptions = true
                    exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
                    showCauses = true
                    showStackTraces = true
                }
            }
        }
        nodejs {
            testTask {
                testLogging {
                    showExceptions = true
                    exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
                    showCauses = true
                    showStackTraces = true
                }
            }
        }
    }

    jvm()
    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation ("org.jetbrains.kotlinx:kotlinx-serialization-core:1.4.0")
                implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
            }
        }
        val jsMain by getting {
            dependencies {

            }
        }

       val jvmMain by getting {
         
            dependencies {}
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}
