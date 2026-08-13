plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.sleepyhead.showcase.tools"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    // android kotlin core
    api(libs.androidx.core.ktx)

    // activities
    api(libs.androidx.appcompat)

    // navigation
    api(libs.androidx.navigation.compose)

    // serialization
    api(libs.kotlin.json.serialization)

    // viewmodel
    api(libs.compose.viewmodel)

    // datetime
    api(libs.kotlinx.date.time)

    api(libs.constraint.layout.compose)
}