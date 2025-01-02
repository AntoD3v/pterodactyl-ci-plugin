package io.github.antod3v.pterodactyl;

import com.mattmalec.pterodactyl4j.PteroBuilder;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Directory;
import com.mattmalec.pterodactyl4j.exceptions.FileUploadException;
import com.mattmalec.pterodactyl4j.exceptions.PteroException;
import com.mattmalec.pterodactyl4j.exceptions.ServerException;
import io.github.antod3v.pterodactyl.extension.Credential;
import io.github.antod3v.pterodactyl.extension.Deploy;
import io.github.antod3v.pterodactyl.extension.Pterodactyl;
import java.io.File;
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

                File fileToUpload;

                try {
                    fileToUpload = deploy.getLocalBuildOutput();
                } catch (RuntimeException exception) {
                    target.getLogger().error("We found a exception for try to get the file to upload", exception);
                    return;
                }

                ClientServer server;

                try {
                    server = createClient(credential);
                } catch (PteroException exception) {
                    target.getLogger().error("We found a exception when try connect to your panel '{}'", credential.getApiUrl(), exception);
                    return;
                }

                String remoteDir = deploy.getRemoteDir();
                if (remoteDir.isEmpty() || !remoteDir.startsWith("/")) {
                    remoteDir = "/" + remoteDir;
                }

                Directory directory;

                try {
                    directory = server.retrieveDirectory(remoteDir).execute();
                } catch (ServerException exception) {
                    target.getLogger().error("We found a exception when try to retrive the remoteDir '{}'", remoteDir, exception);
                    return;
                }

                try {
                    server.getFileManager()
                            .upload(directory)
                            .addFile(fileToUpload, deploy.getRemoteFileName())
                            .execute();
                } catch (FileUploadException exception) {
                    target.getLogger().error("We found a exception for try to upload the file '{}' to '{}'", fileToUpload, remoteDir, exception);
                    return;
                }

                for (String command : deploy.getCommands()) {
                    try {
                        server.sendCommand(command).execute();
                    } catch (PteroException exception) {
                        target.getLogger().error("We found a exception when try to run '{}' in the server", command, exception);
                    }
                }

                target.getLogger().info("Plugin File ({}) deployed!", deploy.getRemoteFileName());
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
