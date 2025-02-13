plugins {
    id("java")
    id("maven-publish")
}

group = "io.gitlab.lone64.framework.bukkit.api"
version = "1.0.0"

dependencies {
    compileOnly("com.iridium", "IridiumColorAPI", "1.0.9")
    compileOnly("com.github.LoneDev6", "API-ItemsAdder", "3.6.1")
    compileOnly("com.googlecode.json-simple", "json-simple", "1.1")
}

tasks.jar {
    archiveFileName.set("framework-api-${version}-release.jar")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.gitlab.lone64"
            artifactId = "framework-api"
            from(components["java"])
        }
    }

    repositories {
        maven("https://maven.lone64.dev/releases/") {
            credentials {
                username = "${properties["MAVEN_USERNAME"]}"
                password = "${properties["MAVEN_PASSWORD"]}"
            }
        }
    }
}