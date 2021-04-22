plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
}

configure<com.android.build.gradle.AppExtension> {
    defaultConfig {
        compileSdkVersion(30)
        minSdkVersion(21)
        targetSdkVersion(30)
        applicationId = "ru.chalexdev.todoapp"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    (this as ExtensionAware).configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions> {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Deps.Jetbrains.Kotlin.StdLib.StdLib)
    implementation(Deps.Google.AndroidMaterial.Material)
    implementation(Deps.AndroidX.AppCompat.AppCompat)
    implementation(Deps.AndroidX.RecyclerView.RecyclerView)
    implementation(Deps.AndroidX.ConstraintLayout.ConstraintLayout)
    implementation(Deps.AndroidX.Core.Ktx)
    implementation(Deps.AndroidX.Lifecycle.ViewModelKtx)
    implementation(Deps.AndroidX.Lifecycle.ViewModel)
    implementation(Deps.AndroidX.Lifecycle.ViewModelSavedState)
    implementation(Deps.AndroidX.Lifecycle.Runtime)
    implementation(Deps.Jetbrains.KotlinX.Coroutines.Core)
    implementation(Deps.Jetbrains.KotlinX.Coroutines.Android)
    implementation(Deps.Squareup.Retrofit2.Retrofit)
    implementation(Deps.Squareup.Retrofit2.ConvertorGson)
}