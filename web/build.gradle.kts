buildscript {

    repositories {
        mavenLocal()
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://s01.oss.sonatype.org/content/repositories/releases")

        maven("https://plugins.gradle.org/m2/")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")


    }
}

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("org.jetbrains.compose")
}


kotlin {


    js(IR) {
        binaries.executable()
        useCommonJs()
        browser {

        }

    }


    sourceSets {
      val  commonMain by getting {
            dependencies {

            }

        }

       val  jsMain by getting{

            dependencies {
                implementation(project(":shared"))
                implementation("org.jetbrains.kotlinx:kotlinx-html-assembly:0.7.3")
                implementation("com.badoo.reaktive:reaktive-js:1.2.1")
                implementation(npm("react", "17.0.2"))
                implementation(npm("@material-ui/core", "4.12.3"))
                implementation(npm("core-js", "3.21.1"))
                implementation(npm("react-qr-code", "2.0.3"))
                implementation(npm("@material-ui/icons", "4.11.2"))
                implementation(npm("inline-style-prefixer", "6.0.0"))
                implementation(npm("uglifyjs-webpack-plugin", "2.2.0"))
            }
        }
    }
}



dependencies {
    "jsMainImplementation"(enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:0.0.1-pre.246-kotlin-1.5.30"))
    "jsMainImplementation"("org.jetbrains.kotlin-wrappers:kotlin-react")
    "jsMainImplementation"("org.jetbrains.kotlin-wrappers:kotlin-react-dom")
    "jsMainImplementation"("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:5.2.0-pre.246-kotlin-1.5.30")
    "jsMainImplementation"("org.jetbrains.kotlin-wrappers:kotlin-styled")
    "jsMainImplementation" ("org.jetbrains.kotlin:kotlin-source-map-loader:1.3.72")
    "jsMainImplementation"("org.jetbrains.kotlin-wrappers:kotlin-csstype:3.0.9-pre.246-kotlin-1.5.30")
    "jsMainImplementation"("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:0.20.0")
    "jsMainImplementation"("net.subroh0508.kotlinmaterialui:core:0.7.0")
    "jsMainImplementation"("net.subroh0508.kotlinmaterialui:lab:0.7.0")


}