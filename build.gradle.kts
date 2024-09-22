import io.izzel.taboolib.gradle.Bukkit
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("io.izzel.taboolib") version "2.0.17"
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
}

taboolib {
    env {
        // 安装模块
        install(
            Bukkit
        )
    }
    version { taboolib = "6.2.0-beta12" }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("ink.ptms.core:v11605:11605")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
