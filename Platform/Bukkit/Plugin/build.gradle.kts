plugins {
    id("java")
    id("com.github.johnrengelman.shadow").version("7.1.2")
}

group = "io.gitlab.lone64.framework.bukkit.plugin"
version = "1.0.0"

dependencies {
    implementation(project(":Platform:Bukkit:API"))
    implementation("com.iridium", "IridiumColorAPI", "1.0.9")

    compileOnly("com.github.LoneDev6", "API-ItemsAdder", "3.6.1")
    compileOnly("com.googlecode.json-simple", "json-simple", "1.1")
}

tasks.shadowJar {
    archiveFileName.set("framework-bukkit-${version}-release.jar")
}