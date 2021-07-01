apply(plugin = "org.gradle.maven-publish")
apply(plugin = "org.gradle.signing")

val jfrogUsername = System.getenv("JFROG_USERNAME") ?: project.findProperty("jfrogUsername").toString()
val jfrogPassword = System.getenv("JFROG_PASSWORD") ?: project.findProperty("jfrogPassword").toString()

val configurePublishing: PublishingExtension.() -> Unit = {
    repositories {
        maven {
            name = "nyper"
            url = uri("https://nycode.jfrog.io/artifactory/nyper/")
            credentials {
                username = jfrogUsername
                password = jfrogPassword
            }
        }
    }
    publications {
        filterIsInstance<MavenPublication>().forEach { publication ->
            publication.pom {
                name.set("Nyper-API")
                url.set("https://github.com/NyCodeGHG/Nyper")
                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/NyCodeGHG/Nyper/issues")
                }
                scm {
                    connection.set("https://github.com/NyCodeGHG/Nyper.git")
                    url.set("https://github.com/NyCodeGHG/Nyper")
                }
                developers {
                    developer {
                        name.set("NyCode")
                        email.set("nico@nycode.de")
                        url.set("https://nycode.de")
                        timezone.set("Europe/Berlin")
                    }
                }
            }
        }
    }
}

val configureSigning: SigningExtension.() -> Unit = {
    val signingKey = System.getenv("SIGNING_KEY") ?: findProperty("signingKey")?.toString()
    val signingPassword = System.getenv("SIGNING_PASSWORD") ?: findProperty("signingPassword")?.toString()
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(
            String(java.util.Base64.getDecoder().decode(signingKey.toByteArray())),
            signingPassword
        )
    }

    publishing.publications.withType<MavenPublication> {
        sign(this)
    }
}

extensions.configure("signing", configureSigning)
extensions.configure("publishing", configurePublishing)

val Project.publishing: PublishingExtension
    get() =
        (this as ExtensionAware).extensions.getByName("publishing") as PublishingExtension