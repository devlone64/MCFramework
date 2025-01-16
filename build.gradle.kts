plugins {
    id("java")
}

group = "io.gitlab.lone64.framework"

allprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://libraries.minecraft.net/")
        maven("https://repo.codemc.io/repository/maven-public/")
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")
    }

    dependencies {
        compileOnly("org.projectlombok", "lombok", "1.18.32")
        annotationProcessor("org.projectlombok", "lombok", "1.18.32")

        compileOnly("net.kyori", "adventure-api", "4.13.0")
        compileOnly("net.kyori", "adventure-text-serializer-legacy", "4.13.0")

        compileOnly("com.mojang", "authlib", "6.0.54")
        compileOnly("org.spigotmc", "spigot-api", "1.20.4-R0.1-SNAPSHOT")
    }
}

java.toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
}