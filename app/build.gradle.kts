plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version "2.0.0"
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.zenitech.imaapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zenitech.imaapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {

        getByName("debug") {
            storeFile = file("IMA_debug.keystore")
        }
        create("release") {
            storeFile = file("IMA.keystore")
            storePassword = "VRdwxxAkHU8uxW0wsGdTjdlmD7q05HsL"
            keyAlias = "IMA"
            keyPassword = "VRdwxxAkHU8uxW0wsGdTjdlmD7q05HsL"
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }

        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    /*
    SHA1: DF:CC:BC:B3:11:26:43:7A:E1:71:BB:B8:7D:6B:AA:7F:98:2A:2E:ED
    SHA256: 84:FC:F8:38:20:7F:42:0D:B5:25:10:DF:8F:C7:B0:68:02:2A:D4:D2:C5:A7:6B:DF:8E:D3:40:F5:C3:47:36:90
    SHA1-BASE64: 38y8sxEmQ3rhcbu4fWuqf5gqLu0=
    */

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    tasks.register<Wrapper>("wrapper") {
        gradleVersion = "8.9"
    }

}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.protolite.well.known.types)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.converter.moshi)
    implementation(libs.androidx.paging.compose)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.google.firebase.analytics)
    implementation(libs.firebase.crashlytics.ktx)

    implementation(libs.androidx.material)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material.icons.extended)

    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.haze.jetpack.compose)

    implementation(libs.barcode.scanning)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.accompanist.permissions)
    implementation(kotlin("reflect"))

    implementation(libs.firebase.auth.ktx)

    implementation(libs.google.firebase.auth.ktx)

    implementation(libs.kotlin.reflect)

    implementation(libs.googleid)

    implementation(libs.play.services.identity)

    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)

    implementation(libs.google.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    implementation(libs.lazytable)

    implementation(libs.androidx.paging.compose.v100alpha16)
    implementation(libs.androidx.paging.runtime)

    implementation(libs.poi.ooxml)

}