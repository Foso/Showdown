
plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("kotlinx-serialization")
}

group "com.example"
version "1.0-SNAPSHOT"
val ktorVersion = "2.0.1"
val ktorfitVersion = "1.0.0-beta06"
repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")

}

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
                implementation("dev.petuska:kmdc-button-js:0.0.4")
                implementation("dev.petuska:kmdc-checkbox-js:0.0.4")
                implementation("dev.petuska:kmdc-switch-js:0.0.4")

                implementation("dev.petuska:kmdc-list-js:0.0.4")
                implementation("dev.petuska:kmdc-snackbar-js:0.0.4")
                implementation("dev.petuska:kmdc-menu-js:0.0.4")
                implementation("dev.petuska:kmdc-textfield-js:0.0.4")
                implementation("dev.petuska:kmdc-dialog-js:0.0.4")

                implementation("dev.petuska:kmdc-icon-button-js:0.0.4")

                implementation("dev.petuska:kmdcx:0.0.4")
               // implementation(npm("material-icons","^1.10.4"))
                // SCSS dependencies
                implementation(devNpm("style-loader", "^3.3.1"))
                implementation(devNpm("css-loader", "^6.7.1"))
                implementation(devNpm("sass-loader", "^13.0.0"))
                implementation(devNpm("sass", "^1.52.1"))
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
