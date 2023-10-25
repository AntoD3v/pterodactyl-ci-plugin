package fr.antod3v.plugins.pterodactyl.task

import com.mattmalec.pterodactyl4j.PteroBuilder
import fr.antod3v.plugins.pterodactyl.extension.Credential
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.options.Option

abstract class AbstractTask : DefaultTask() {

    protected val extension = project.extensions.findByName("pterodactyl") as Credential

    @Option(option = "serverId", description = "Id of the server (8 first characters of uuid)")
    protected var serverId = extension.serverId

    @Option(option = "apiUrl", description = "Url of the pterodactyl api")
    protected var apiUrl = extension.apiUrl

    @Option(option = "apiKey", description = "Key of the pterodactyl api")
    protected var apiKey = extension.apiKey

    init {
        group = "pterodactyl"
    }

    protected fun createClient() = PteroBuilder
        .createClient(apiUrl, apiKey)
        .retrieveServerByIdentifier(serverId)
        .execute()

}