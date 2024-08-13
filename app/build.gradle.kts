plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.calorific2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.calorific2"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.volley)
    implementation (libs.glide)
    implementation (libs.opencsv)
    annotationProcessor (libs.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.firebase.ui.auth)

    implementation("com.google.firebase:firebase-auth")

    // Import the Firebase BoM
    implementation(libs.firebase.bom)
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.firebaseui:firebase-ui-auth:7.2.0")
    implementation (libs.firebase.firestore)

}