package fr.antod3v.plugins.pterodactyl

import fr.antod3v.plugins.pterodactyl.extension.Credential
import fr.antod3v.plugins.pterodactyl.task.DeployTask
import fr.antod3v.plugins.pterodactyl.task.ExecTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class Pterodactyl : Plugin<Project> {

    override fun apply(project: Project) {

        val credential = project.extensions.create("pterodactyl", Credential::class.java, project)

        project.tasks.create("deploy", DeployTask::class.java) {

            it.apiUrl.set(credential.apiUrl)
            it.apiKey.set(credential.apiKey)
            it.serverId.set(credential.apiKey)

        }

        project.tasks.create("exec", ExecTask::class.java) {

            it.apiUrl.set(credential.apiUrl)
            it.apiKey.set(credential.apiKey)
            it.serverId.set(credential.apiKey)

        }

    }

}