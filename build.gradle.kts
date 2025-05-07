buildscript {
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    //id("com.android.application") version "8.7.3" apply false
    alias(libs.plugins.android.application) apply false
    //id("com.android.library") version "8.2.1" apply false
    // 1.9.10 2.0.20
    //id("org.jetbrains.kotlin.android") version "2.0.20" apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    //id("com.google.dagger.hilt.android") version "2.42" apply false
    // 1.9.0-1.0.13
    //id("com.google.devtools.ksp") version "2.0.20-1.0.25" apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
}