package fr.antod3v.plugins.pterodactyl.extension

import org.gradle.api.Project
import org.gradle.api.provider.Property
import javax.inject.Inject


abstract class Credential @Inject constructor (project: Project) {


    val apiUrl: Property<String> = project.objects.property(String::class.java)
    val apiKey: Property<String> = project.objects.property(String::class.java)
    val serverId: Property<String> = project.objects.property(String::class.java)

}