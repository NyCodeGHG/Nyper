pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "Nyper"

include("Nyper-API", "Nyper-Server")
include("test-plugin")
