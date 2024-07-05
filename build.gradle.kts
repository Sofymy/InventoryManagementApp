// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.jetbrains.kotlin.serialization) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    kotlin("kapt") version "2.0.0"
}

buildscript {
    dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }

    repositories {
        google()
        mavenCentral()
    }
}