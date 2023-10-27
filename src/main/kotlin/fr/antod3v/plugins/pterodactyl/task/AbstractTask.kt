package fr.antod3v.plugins.pterodactyl.task

import com.mattmalec.pterodactyl4j.PteroBuilder
import com.mattmalec.pterodactyl4j.client.entities.ClientServer
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.options.Option

abstract class AbstractTask : DefaultTask() {

    @get:Input
    @get:Option(option = "serverId", description = "Id of the server (8 first characters of uuid)")
    abstract val serverId: Property<String>

    @get:Input
    @get:Option(option = "apiUrl", description = "Url of the pterodactyl api")
    abstract val apiUrl: Property<String>

    @get:Input
    @get:Option(option = "apiKey", description = "Key of the pterodactyl api")
    abstract val apiKey: Property<String>

    init {
        group = "pterodactyl"
    }

    protected fun createClient(): ClientServer = PteroBuilder
        .createClient(apiUrl.get(), apiKey.get())
        .retrieveServerByIdentifier(serverId.get())
        .execute()

}