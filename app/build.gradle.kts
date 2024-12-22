plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")


}

android {
    namespace = "com.connectivitychecker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.connectivitychecker"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
}

dependencies {

    val composeBom = platform("androidx.compose:compose-bom:2024.10.01")
    implementation(composeBom)
    testImplementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.compose.foundation:foundation")

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.5")

    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")


    // Kotlin
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.9.24")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Android
    implementation( "androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation ("androidx.activity:activity-compose:1.9.3")

    // Volley
    implementation ("com.android.volley:volley:1.2.1")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")


    // Testing
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:4.5.1")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")


    // Compose
    implementation ("androidx.compose.ui:ui:1.7.6")
    implementation ("androidx.compose.material3:material3:1.3.1")


}
