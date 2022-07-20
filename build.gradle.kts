// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.6.21" apply false
    id("org.jetbrains.kotlin.plugin.serialization")
}


allprojects {

    repositories {
        google()
        mavenCentral()
        maven(url = ("https://maven.pkg.jetbrains.space/public/p/compose/dev"))
        maven(url = "https://s01.oss.sonatype.org/content/repositories/releases")
    }

}

