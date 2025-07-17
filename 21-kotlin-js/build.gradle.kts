plugins {
    kotlin("js") version "1.9.23"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        browser {
            binaries.executable()
            webpackTask {
                output.libraryTarget = "umd"
            }
            runTask {
                outputFileName = "index.js"
            }
        }
    }
    sourceSets {
        val main by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        val test by getting
    }
}

// For running tests in browsers
// tasks.named<org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest>("jsTest") {
//    useKarma {
//        useChromeHeadless()
//    }
// }
