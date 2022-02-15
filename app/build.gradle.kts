import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Configurations.COMPILE_SDK
    defaultConfig {
        applicationId = Configurations.APPLICATION_ID
        minSdk = Configurations.MIN_SDK_VERSION
        targetSdk = Configurations.TARGET_SDK
        versionCode = Configurations.VERSION_CODE
        versionName = Configurations.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes.onEach{
        val properties = Properties()
        properties.load(project.rootProject.file("apikey.properties").inputStream())
        val apiKey = properties.getProperty("movie_database_apikey", "")
        val googleMapsApiKey = properties.getProperty("google_maps_apikey", "")
        it.buildConfigField("String", "MOVIE_DB_APIKEY", apiKey)
        it.buildConfigField("String", "GOOGLE_MAPS_APIKEY", googleMapsApiKey)
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = Configurations.java_version
        targetCompatibility = Configurations.java_version
    }
    kotlinOptions {
        jvmTarget = Configurations.JVM_TARGET
    }

    buildFeatures {
        viewBinding = true
    }

}

dependencies{
    /**ViewBinding Delegate**/
    implementation(ViewBindingDelegate.DELEGATE)
    /**Kotlin**/
    implementation(Kotlin.CORE)
    implementation(Kotlin.STDLIB)
    /**Design**/
    implementation(Design.CONSTRAINT_LAYOUT)
    implementation(Design.MATERIAL)
    implementation(Design.APPCOMPAT)
    implementation(Design.SWIPE_REFRESH)
    /** Koin **/
    implementation(Koin.CORE)
    implementation(Koin.ANDROID)
    implementation(Koin.ANDROID_COMPAT)
    /**Retrofit**/
    implementation(Retrofit2.RETROFIT)
    implementation(Retrofit2.CONVERTER_JSON)
    implementation(Retrofit2.COROUTINES_ADAPTER)
    implementation(Retrofit2.LOGGING_INTERCEPTOR)
    /**Google Map**/
    implementation(GoogleMaps.googleMap)
    implementation(GoogleMaps.googleLocation)
    /**Room**/
    kapt(Room.COMPILER)
    implementation(Room.RUN_TIME)
    implementation(Room.KTX)
    /**Picasso**/
    implementation(ImageLoaders.picasso)
    /**Firebase**/
    implementation(Firebase.core)
    implementation(Firebase.messaging)
    implementation(Firebase.analytics)
    /**Tests**/
    testImplementation(Tests.JUNIT)
    androidTestImplementation(Tests.TEST_EXT_JUNIT)
    androidTestImplementation(Tests.ESPRESSO)
}