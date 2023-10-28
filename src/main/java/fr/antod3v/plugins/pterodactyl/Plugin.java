package fr.antod3v.plugins.pterodactyl;

import com.mattmalec.pterodactyl4j.PteroBuilder;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Directory;
import fr.antod3v.plugins.pterodactyl.extension.Credential;
import fr.antod3v.plugins.pterodactyl.extension.Deploy;
import fr.antod3v.plugins.pterodactyl.extension.Pterodactyl;
import org.gradle.api.Project;

import java.io.File;

public class Plugin implements org.gradle.api.Plugin<Project> {

    @Override
    public void apply(Project target) {

        Pterodactyl pterodactyl = target.getExtensions().create("pterodactyl", Pterodactyl.class, target);

        target.getTasks().create("deploy", task -> {

            task.setGroup("pterodactyl");

            task.doLast((t) -> {

                Credential credential = pterodactyl.getCredential();
                Deploy deploy = pterodactyl.getDeploy();

                ClientServer server = createClient(credential);

                Directory directory = server.retrieveDirectory().execute().getDirectoryByName(deploy.getRemoteDir()).orElse(null);

                if (directory == null) {
                    System.out.println("Plugins directory (" + deploy.getRemoteDir() + ") not found.");
                    return;
                }

                server.getFileManager()
                        .upload(directory)
                        .addFile(deploy.getLocalBuildOuput(), deploy.getRemoteFileName())
                        .execute();

                deploy.getCommands().forEach(command -> server.sendCommand(command).execute());

                System.out.println("Plugin deployed !");
            });

        });

    }

    protected ClientServer createClient(Credential credential) {

        return PteroBuilder.createClient(credential.getApiUrl(), credential.getApiKey())
                .retrieveServerByIdentifier(credential.getServerId())
                .execute();

    }
}
