import java.net.URL

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    kotlin("kapt") version "1.8.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10" apply false
    val room_version = "2.6.1"
    id("androidx.room") version room_version apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}
