plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.randuserface"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.randuserface"
        minSdk = 29
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // AndroidX and Google Libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigationFragment)
    implementation(libs.navigationUi)
    implementation(libs.playServicesMaps)
    implementation(libs.playServicesLocation)

    // Retrofit and Picasso
    implementation(libs.retrofit)
    implementation(libs.gsonConverter)
    implementation(libs.picasso)

    // Testing Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidxJunit)
    androidTestImplementation(libs.espressoCore)
}
