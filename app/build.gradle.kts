import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("kotlin-kapt")
}
val keystorePropertiesFile = rootProject.file("keystore.properties")

// Initialize a new Properties() object called keystoreProperties.
val keystoreProperties = Properties()

// Load your keystore.properties file into the keystoreProperties object.
keystoreProperties.load(FileInputStream(keystorePropertiesFile))
android {
    namespace = "com.harrypotter.app"
    compileSdk = 34
    packaging { resources.excludes.add("META-INF/*") }
    defaultConfig {
        applicationId = "com.harrypotter.app"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled =false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
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
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui.test.junit4.android)
    testImplementation(libs.junit)
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.11.0")
    implementation ("com.squareup.moshi:moshi:1.15.0")
    implementation ("com.squareup.moshi:moshi-kotlin:1.15.0")

    // Koin
    implementation ("io.insert-koin:koin-core:4.0.0")
    implementation("io.insert-koin:koin-android:4.0.0")
    implementation( "io.insert-koin:koin-androidx-compose:4.0.0")
    kapt ("androidx.room:room-compiler:2.5.0")

    // Cache Image
    implementation("io.coil-kt:coil-compose:2.4.0")

    //Pull To Refresh
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.32.0")

    //Test Dependencies
    testImplementation( "junit:junit:4.13.2")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation ("androidx.test.ext:junit:1.2.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation ("org.mockito:mockito-core:5.3.0")
    androidTestImplementation( "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    androidTestImplementation ("androidx.test:rules:1.6.1")
    testImplementation ("io.mockk:mockk:1.13.12")
    androidTestImplementation ("io.mockk:mockk-android:1.13.12")
    androidTestImplementation ("com.squareup.okhttp3:mockwebserver:4.9.1")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.2")

}