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

@Getter
@Setter
public class Deploy {

    private final Project project;
    private String localBuildOuput = null;
    private String remoteDir = "plugins";
    private String remoteFileName = null;

    private List<String> commands = null;

    public Deploy(Project project) {
        this.project = project;
    }

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

    public String getRemoteFileName() {

        return this.remoteFileName == null
                ? project.getName() + "-" + project.getVersion() + ".jar"
                : this.remoteFileName;

    }

    public List<String> getCommands() {

        return this.commands == null ? Arrays.asList(usePlugmanCommand(), "broadcast &fDeploiement de &e"+getPluginName()+" &f: &a✔ réussi") : this.commands;

    }

    public String usePlugmanCommand() {

        return "plugman reload " + getPluginName();

    }

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
