package fr.antod3v.plugins.pterodactyl.task

import com.mattmalec.pterodactyl4j.PteroBuilder
import com.mattmalec.pterodactyl4j.client.entities.ClientServer
import fr.antod3v.plugins.pterodactyl.extension.Credential
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.options.Option

abstract class AbstractTask : DefaultTask() {

    private val extension = project.extensions.findByName("pterodactyl") as Credential

    @Input
    @Option(option = "serverId", description = "Id of the server (8 first characters of uuid)")
    var serverId: String? = extension.serverId

    @Input
    @Option(option = "apiUrl", description = "Url of the pterodactyl api")
    var apiUrl: String? = extension.apiUrl

    @Input
    @Option(option = "apiKey", description = "Key of the pterodactyl api")
    var apiKey: String? = extension.apiKey

    init {
        group = "pterodactyl"
    }

    protected fun createClient(): ClientServer = PteroBuilder
        .createClient(apiUrl, apiKey)
        .retrieveServerByIdentifier(serverId)
        .execute()

}