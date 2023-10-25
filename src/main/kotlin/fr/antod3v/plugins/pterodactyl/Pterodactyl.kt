package fr.antod3v.plugins.pterodactyl

import fr.antod3v.plugins.pterodactyl.extension.Credential
import fr.antod3v.plugins.pterodactyl.task.DeployTask
import fr.antod3v.plugins.pterodactyl.task.ExecTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class Pterodactyl : Plugin<Project> {

    override fun apply(project: Project) {

        project.extensions.create("pterodactyl", Credential::class.java)

        project.tasks.create("deploy", DeployTask::class.java)
        project.tasks.create("exec", ExecTask::class.java)

    }

}