package fr.antod3v.plugins.pterodactyl.task

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.util.function.Consumer

abstract class ExecTask : AbstractTask() {

    @get:Input
    @get:Option(option = "commands", description = "Commands to execute")
    abstract val commands: Property<List<String>>

    @TaskAction
    fun exec() {

        print("Executing commands...")

        createClient().let {
            commands.get().forEach(Consumer { command: String? -> it.sendCommand(command).execute() })
        }

        println("done !")

    }
}
