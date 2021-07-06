job("Build and Publish API") {{
    env["JFROG_USERNAME"] = Params("jfrog_username")
    env["JFROG_PASSWORD"] = Secrets("jfrog_password")
    env["SIGNING_KEY"] = Secrets("signing_key")
    env["SIGING_PASSWORD"] = Secrets("signing_password")
    kotlinScript { api ->
        api.gradlew("applyPatches")
        api.gradlew("publish")
    }
}

job("Build Paperclip Jar") {
    kotlinScript { api ->
        api.gradlew("applyPatches")
        api.gradlew("paperclipJar")
    }
}
