import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
    alias(libs.plugins.serialization.plugin)
}

group = "com.noxapps.carpetScheduler"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")
        }
    }
}

kotlin {
    configAsKobwebApplication("carpetScheduler", includeServer = true)

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
        }

        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)
            implementation(libs.kobwebx.markdown)
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
            implementation("dev.gitlive:firebase-database:1.11.1")
            implementation("dev.gitlive:firebase-storage:1.11.1")


        }
        jvmMain.dependencies {
            implementation(libs.kotlinx.serialization)
            compileOnly(libs.kobweb.api) // Provided by Kobweb backend at runtime

        }
    }
}
