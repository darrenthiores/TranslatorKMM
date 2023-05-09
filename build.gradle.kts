buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Deps.kotlinGradlePlugin)
        classpath(Deps.androidBuildTools)
        classpath(Deps.sqlDelightGradlePlugin)
        classpath(Deps.hiltGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.3.1").apply(false)
    id("com.android.library").version("7.3.1").apply(false)
    kotlin("android").version(Deps.kotlinVersion).apply(false)
    kotlin("multiplatform").version(Deps.kotlinVersion).apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
