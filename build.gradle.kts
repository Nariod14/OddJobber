// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
   // id("com.android.application") version "8.1.1" apply false
    id ("com.google.gms.google-services") version "4.4.0" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
