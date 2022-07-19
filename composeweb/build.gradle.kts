import org.jetbrains.compose.jetbrainsCompose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("kotlinx-serialization")
}

group "com.example"
version "1.0-SNAPSHOT"
repositories {
    google()
    mavenCentral()
    this.jetbrainsCompose()
   // maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")

}

val kmdcVersion = "0.0.4"

kotlin {
    js(IR) {
        browser {
            testTask {
                testLogging.showStandardStreams = true
                useKarma {
                    useChromeHeadless()
                    useFirefox()
                }
            }
        }
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {

            }
        }
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.web.core)
                implementation(compose.runtime)

                implementation("com.badoo.reaktive:reaktive-js:1.2.2")

                implementation("app.softwork:routing-compose:0.2.4")
              //  implementation("dev.petuska:kmdc:0.0.4")
                implementation("dev.petuska:kmdc-button-js:$kmdcVersion")
                implementation("dev.petuska:kmdc-checkbox-js:$kmdcVersion")
                implementation("dev.petuska:kmdc-switch-js:$kmdcVersion")

                implementation("dev.petuska:kmdc-list-js:$kmdcVersion")
                implementation("dev.petuska:kmdc-snackbar-js:$kmdcVersion")
                implementation("dev.petuska:kmdc-menu-js:$kmdcVersion")
                implementation("dev.petuska:kmdc-textfield-js:$kmdcVersion")
                implementation("dev.petuska:kmdc-dialog-js:$kmdcVersion")

                implementation("dev.petuska:kmdc-icon-button-js:$kmdcVersion")

                implementation("dev.petuska:kmdcx:$kmdcVersion")
               // implementation(npm("material-icons","^1.10.4"))
                // SCSS dependencies
                implementation(devNpm("style-loader", "3.3.1"))
                implementation(devNpm("css-loader", "6.7.1"))
                implementation(devNpm("sass-loader", "13.0.0"))
                implementation(devNpm("sass", "1.52.1"))
                implementation(devNpm("uglifyjs-webpack-plugin", "2.2.0"))

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
    versions.webpackCli.version = "4.10.0"
}

task("prepareKotlinBuildScriptModel")  {}