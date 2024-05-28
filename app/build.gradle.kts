import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    id(libs.plugins.kotlinParcelize.get().pluginId)
    id(libs.plugins.kaptKotlin.get().pluginId)
    id("com.google.devtools.ksp") version "1.9.23-1.0.20" apply true
}

android {
    namespace = "com.sofascore.minisofa"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sofascore.minisofa"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.retrofit.client)
    implementation(libs.retrofit.gson)
    implementation(libs.okHttp.client)
    implementation(libs.okHttp.logging)
    implementation(libs.play.services.location)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.room.paging)
    implementation(libs.androidx.paging.common.android)
    implementation(libs.androidx.databinding.common)
    implementation(libs.androidx.paging.runtime.ktx)
    ksp(libs.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.preference)
    implementation(libs.androidx.activity)
    implementation(libs.avatarview.coil)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.glide)
    kapt(libs.glide.compiler)
}

kapt {
    correctErrorTypes = true
}
