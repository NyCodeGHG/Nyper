plugins {
    java
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    maven("https://schlaubi.jfrog.io/artifactory/mojang_api")
}

dependencies {
    compileOnly("org.spigotmc", "spigot-api", "1.17-R0.1-SNAPSHOT")
    compileOnly("dev.schlaubi", "mojang_api", "1.0-SNAPSHOT")
    compileOnly("org.jetbrains", "annotations", "21.0.1")
}

tasks {
    processResources {
        from(sourceSets["main"].resources) {
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
        }
    }
}
