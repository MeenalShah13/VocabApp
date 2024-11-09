// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

buildscript {
    dependencies {
        // Add this line for Google Services
        classpath("com.google.gms:google-services:4.3.15") // Check for the latest version
    }
}