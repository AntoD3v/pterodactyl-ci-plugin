package fr.antod3v.plugins.pterodactyl.task

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File
import kotlin.jvm.optionals.getOrNull

abstract class DeployTask : AbstractTask() {

    @get:Input
    @get:Option(option = "buildPath", description = "Path to the Minecraft server build")
    abstract val buildPath: Property<String>

    @get:Input
    @get:Optional
    @get:Option(option = "commands", description = "Commands to reload")
    val commands: Property<List<String>>? = null;

    @get:Input
    @get:Optional
    @get:Option(option = "targetDir", description = "Directory to the pterodactyl server")
    val targetDir: String = "plugins"

    @get:Input
    @get:Optional
    @get:Option(option = "targetName", description = "Remote name of the build (if null, use the local name)")
    val targetName: String? = null


    @TaskAction
    fun deploy() {

        print("Deployment on $apiUrl - $serverId ...")

        val server = createClient()

        val directory = server.retrieveDirectory().execute().getDirectoryByName(targetDir).getOrNull() ?: run {
            println(
                "Plugins directory ($targetDir) not found."
            ); return
        }

        val fileBuild = this.buildPath.get().let { File(it).takeIf { file: File -> file.exists() } }
            ?: run { println("buildPath field is missing."); return }

        server.fileManager
            .upload(directory)
            .addFile(fileBuild, targetName ?: fileBuild.getName())
            .execute()

        commands?.get()?.forEach { command: String? -> server.sendCommand(command).execute() }

        println("Done !")

    }



}