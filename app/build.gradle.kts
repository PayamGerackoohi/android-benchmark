@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    kotlin("kapt")
    jacoco
}
val packageName = "com.payamgr.androidbenchmark"

jacoco { toolVersion = "0.8.11" }

android {
    namespace = packageName
    compileSdk = 34

    defaultConfig {
        applicationId = packageName
        minSdk = 24
        targetSdk = 33
        versionCode = Versions.app.code
        versionName = Versions.app.name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    Versions.apply {
        compileOptions {
            sourceCompatibility = java
            targetCompatibility = java
        }
        kotlinOptions {
            jvmTarget = java.toString()
        }
        kotlin {
            jvmToolchain(java.ordinal + 1)
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/LICENSE.md"
            excludes += "/META-INF/LICENSE-notice.md"
        }
    }
    testOptions {
        packaging {
            jniLibs {
                useLegacyPackaging = true // mockK
            }
        }
    }
}

Versions.dependencies {
    dependencies {
        implementation("androidx.core:core-ktx:${android.core}")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:${android.kotlin.lifecycleRuntime}")
        implementation("androidx.activity:activity-compose:${android.compose.activity}")
        implementation(platform("androidx.compose:compose-bom:${android.compose.bom}"))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.material3:material3")
        implementation("androidx.compose.material:material-icons-extended:${android.compose.icons}")
        implementation("com.airbnb.android:mavericks:$mavericks")
        implementation("com.airbnb.android:mavericks-compose:$mavericks")
        implementation("com.airbnb.android:mavericks-hilt:$mavericks")
        implementation("com.google.dagger:hilt-android:$hilt")
        kapt("com.google.dagger:hilt-android-compiler:$hilt")
        testImplementation("org.junit.jupiter:junit-jupiter:${test.jUnit}")
        testImplementation("androidx.test:runner:${test.android.runner}")
        testImplementation("org.assertj:assertj-core:${test.assertj}")
        testImplementation("io.mockk:mockk-android:${test.mockk}")
        androidTestImplementation("androidx.test:runner:${test.android.runner}")
        androidTestImplementation("androidx.test.espresso:espresso-core:${test.espresso}")
        androidTestImplementation(platform("androidx.compose:compose-bom:${android.compose.bom}"))
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
        androidTestImplementation("org.assertj:assertj-core:${test.assertj}")
        androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${test.kotlin.coroutine}")
        androidTestImplementation("io.mockk:mockk-android:${test.mockk}")
        androidTestImplementation("com.airbnb.android:mavericks-testing:$mavericks")
        androidTestImplementation("com.airbnb.android:mavericks-mocking:$mavericks")
        debugImplementation("androidx.compose.ui:ui-tooling")
        debugImplementation("androidx.compose.ui:ui-test-manifest")
    }
}

kapt {
    correctErrorTypes = true
}

tasks.withType<Test> {
    useJUnitPlatform()
    extensions.configure(JacocoTaskExtension::class) {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

tasks.register("jacocoCoverage", JacocoReport::class) {
    val testTaskName = "testDebugUnitTest"
    dependsOn(testTaskName)

    reports {
        html.required.set(true)
        xml.required.set(false)
        csv.required.set(false)
    }

    val sourceBase = "com/payamgr/androidbenchmark"
    val excludes = listOf(
        "$sourceBase/AndroidBenchmarkApplication*",
        "$sourceBase/data/model/**",
        "$sourceBase/ui/**",
    )

    sourceDirectories.setFrom("${project.projectDir}/src/main/java")
    classDirectories.setFrom(files(fileTree("$buildDir/tmp/kotlin-classes/debug") { exclude(excludes) }))
    executionData.setFrom(file("$buildDir/jacoco/$testTaskName.exec"))
}

tasks.register("makeTestReport") {
    dependsOn("connectedDebugAndroidTest", "jacocoCoverage")
    doLast {
        val folder = "reports"
        val source = "$buildDir/$folder"
        val destination = "${project.projectDir}/$folder"
        delete(destination)
        copy {
            from(source)
            into(destination)
        }
    }
}
