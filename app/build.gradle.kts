plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    //kotlin("kapt")
    //id("kotlin-kapt")
    //id("com.google.dagger.hilt.android")
    //id("dagger.hilt.android.plugin")
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.parcelize)
    //id("kotlin-parcelize")
    //id("com.google.devtools.ksp")
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
            excludes += "META-INF/DEPENDENCIES"
        }
    }
    namespace = "com.mrgranfiesta.ponteenformaguerrero3"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mrgranfiesta.ponteenformaguerrero3"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        //testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.mrgranfiesta.ponteenformaguerrero3.TestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
            }
        }
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //1.7.0 1.9.0 1.12.0 1.13.0
    implementation(libs.androidx.core.ktx)
    //2.3.1 2.5.1 2.6.2 2.7.0
    implementation(libs.androidx.lifecycle.runtime.ktx)
    //1.2.0 1.3.1 1.5.1 1.7.2 1.8.2 1.9.0
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    //1.0.1 1.2.0-rc01 1.2.1
    implementation(libs.androidx.material3)
    //1.0.1
    implementation(libs.androidx.material3.window)
    //2.5.3 2.7.5 2.7.6 2.7.7
    implementation(libs.androidx.navigation.compose)
    //Fix Bug
    implementation(platform(libs.kotlin.bom))

    //Icons 1.5.2 1.5.4 1.6.0 1.6.6
    implementation(libs.androidx.material.icons.extended)

    // Constraint 1.0.1
    implementation(libs.androidx.constraintlayout.compose)

    // ROOM 2.4.0 2.5.2 2.6.1
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    //1.6.4 1.7.1 1.7.3
    implementation(libs.kotlinx.coroutines.android)

    // GSON 2.8.9 2.10 2.10.1
    implementation(libs.gson)

    // HITL 2.42 2.48 2.49
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // 1.1.0 1.2.0
    implementation(libs.androidx.hilt.navigation.compose)

    // Kotlin Android Extensions Parcelable
    //implementation("org.jetbrains.kotlinx:kotlinx-parcelize:1.5.0")

    //Coil 2.4.0 2.5.0
    implementation(libs.coil.compose)

    //PickVisualMedia contract "1.9.0"
    implementation(libs.androidx.activity.ktx)

    //DATA STORE
    implementation(libs.androidx.datastore.datastore.preferences)

    implementation(kotlin("reflect"))
    //implementation("org.jetbrains.kotlin:kotlin-reflect:2.1.10")
    //Acompanish 0.31.0-alpha 0.34.0
    /*val acompanish = "0.34.0"
    implementation("com.google.accompanist:accompanist-permissions:$acompanish")*/

    //WorkManager
    //implementation(libs.androidx.work.rutime.ktx)

    //Mockk para mocks
    testImplementation(libs.io.mockk)

    //robolectric
    testImplementation(libs.org.robolectric)

    //Junit 4.13.2
    testImplementation(libs.junit)

    testImplementation(libs.androidx.core)
    testImplementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(libs.io.mockk.android)
    androidTestImplementation(libs.androidx.datastore.datastore.preferences)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.hilt.test)
    kspAndroidTest(libs.hilt.compiler.test)
    androidTestImplementation(libs.androidx.arch.core)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}