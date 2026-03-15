plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget()

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Coroutines for reactive database queries
            implementation(libs.kotlinx.coroutines.core)
            // SQLDelight coroutines extensions (asFlow, mapToList, etc.)
            implementation(libs.sqldelight.coroutines)
            // DateTime library for cross-platform timestamps
            implementation(libs.kotlinx.datetime)
        }
        androidMain.dependencies {
            // SQLDelight driver for Android (uses Android's built-in SQLite)
            implementation(libs.sqldelight.android.driver)
        }
        iosMain.dependencies {
            // SQLDelight driver for iOS (uses SQLite bundled with iOS)
            implementation(libs.sqldelight.native.driver)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

// SQLDelight configuration
// This tells SQLDelight to generate a database class called "AppDatabase"
// The generated code will be placed in the specified package
sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.kemalcodes.kmptutorial.db")
        }
    }
}

android {
    namespace = "com.kemalcodes.kmptutorial.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
