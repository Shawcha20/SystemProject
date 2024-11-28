// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
//        classpath("com.google.gms:google-services:4.4.1")
        //shawcha added
        val nav_version = "2.3.5"
        val kotlin_version="1.5.21"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath("com.android.tools.build:gradle:8.1.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        //endb
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}

