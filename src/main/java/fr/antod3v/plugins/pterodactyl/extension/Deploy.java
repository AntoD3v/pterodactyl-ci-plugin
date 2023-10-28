package fr.antod3v.plugins.pterodactyl.extension;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.gradle.api.Project;
import org.gradle.internal.impldep.org.testng.collections.Lists;

import javax.annotation.RegEx;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Deploy represents the deployment process for a project.
 * It provides methods for getting the local build output file, the remote file name,
 * the list of deployment commands, and executing a specific plugman command.
 */
@Getter
@Setter
public class Deploy {

    private final Project project;
    private String localBuildOuput = null;
    private String remoteDir = "plugins";
    private String remoteFileName = null;

    private List<String> commands = null;

    /**
     * Initializes a new instance of the Deploy class with the specified project.
     *
     * @param project The project to be deployed.
     */
    public Deploy(Project project) {
        this.project = project;
    }

    /**
     * Retrieves the local build output file.
     *
     * @return the local build output file
     */
    public File getLocalBuildOuput() {

        if (this.localBuildOuput == null) {

            File dir = new File(project.getProjectDir(), "build/libs");
            File[] files = dir.listFiles();

            if (files.length == 0) {
                System.out.println("No file found in build/libs");
                return null;
            }

            File current = files[0];
            long size = current.getTotalSpace();

            for (File file : files) {

                if (file.getTotalSpace() > size) {
                    current = file;
                    size = file.getTotalSpace();
                }

            }

            return current;

        }

        return new File(this.project.getProjectDir(), this.localBuildOuput);

    }

    /**
     * Retrieves the name of the remote file.
     *
     * @return the name of the remote file
     */
    public String getRemoteFileName() {

        return this.remoteFileName == null
                ? project.getName() + "-" + project.getVersion() + ".jar"
                : this.remoteFileName;

    }

    /**
     * Retrieves the list of commands to execute.
     *
     * @return the list of commands to execute
     */
    public List<String> getCommands() {

        return this.commands == null ? Arrays.asList(usePlugmanCommand(), "broadcast &fDeploiement de &e"+getPluginName()+" &f: &a✔ réussi") : this.commands;

    }

    /**
     * Retrieves the Plugman command to reload the plugin.
     *
     * @return the Plugman command to reload the plugin
     */
    public String usePlugmanCommand() {

        return "plugman reload " + getPluginName();

    }

    /**
     * Retrieves the name of the plugin from the plugin.yml file.
     *
     * @return the name of the plugin
     * @throws IllegalStateException if the plugin.yml file is not found or does not have a name field
     */
    @SneakyThrows
    public String getPluginName() {

        File pluginYml = new File(project.getProjectDir(), "src/main/resources/plugin.yml");

        if (!pluginYml.exists()) {
            throw new IllegalStateException("plugin.yml not found ("+pluginYml.getAbsolutePath()+").");
        }

        String content = new String(Files.readAllBytes(pluginYml.toPath()));

        Matcher matcher = Pattern.compile("name: (.+)").matcher(content);

        if (!matcher.find()) {
            throw new IllegalStateException("plugin.yml doesn't have name field");
        }

        return matcher.group(1);

    }
}
