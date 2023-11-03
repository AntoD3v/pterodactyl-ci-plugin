package io.github.antod3v.pterodactyl;

import com.mattmalec.pterodactyl4j.PteroBuilder;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Directory;
import io.github.antod3v.pterodactyl.extension.Credential;
import io.github.antod3v.pterodactyl.extension.Deploy;
import io.github.antod3v.pterodactyl.extension.Pterodactyl;
import org.gradle.api.Project;

/**
 * The Plugin class is responsible for applying the Pterodactyl configuration to a Gradle project.
 * It implements the org.gradle.api.Plugin interface.
 * This class provides the "deploy" task for uploading files and executing commands on a Pterodactyl server.
 */
public class Plugin implements org.gradle.api.Plugin<Project> {

    /**
     * Apply the Pterodactyl configuration to the given project.
     *
     * @param target The project to apply the Pterodactyl configuration to.
     */
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

    /**
     * Creates a Pterodactyl client server using the provided credentials.
     *
     * @param credential The credential object containing the API URL, API key, and server identifier.
     * @return The created Pterodactyl client server.
     */
    protected ClientServer createClient(Credential credential) {

        return PteroBuilder.createClient(credential.getApiUrl(), credential.getApiKey())
                .retrieveServerByIdentifier(credential.getServerId())
                .execute();

    }
}
