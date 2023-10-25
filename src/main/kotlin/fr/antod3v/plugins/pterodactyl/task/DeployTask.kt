package fr.antod3v.plugins.pterodactyl.task

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File
import kotlin.jvm.optionals.getOrNull

open class DeployTask : AbstractTask() {

    @Input
    @Option(option = "buildPath", description = "Path to the Minecraft server build")
    var buildPath: String? = null

    @Input
    @Optional
    @Option(option = "targetDir", description = "Directory to the pterodactyl server")
    var targetDir: String? = "plugins"

    @Input
    @Optional
    @Option(option = "targetName", description = "Remote name of the build")
    var targetName: String? = null

    @Input
    @Option(option = "command", description = "Command to reload")
    var command: String? = null

    @TaskAction
    fun deploy() {

        print("Deployment on $apiUrl - $serverId ...")

        val server = createClient()

        val directory = server.retrieveDirectory().execute().getDirectoryByName(targetDir).getOrNull() ?: run {
            println(
                "Plugins directory ($targetDir) not found."
            ); return
        }

        val fileBuild = this.buildPath?.let { File(it).takeIf { file: File -> file.exists() } }
            ?: run { println("buildPath field is missing."); return }

        server.fileManager
            .upload(directory)
            .addFile(fileBuild, targetName ?: fileBuild.getName())
            .execute()

        server.sendCommand(command).execute()

        println("Done !")

    }

}