package fr.antod3v.plugins.pterodactyl.task

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.util.function.Consumer

open class ExecTask : AbstractTask() {

    @Input
    @Option(option = "commands", description = "Commands to execute")
    var commands: List<String> = ArrayList()

    @TaskAction
    fun exec() {

        print("Executing commands...")

        createClient().let {
            commands.forEach(Consumer { command: String? -> it.sendCommand(command).execute() })
        }

        println("done !")

    }
}
