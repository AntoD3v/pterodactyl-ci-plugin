package io.github.antod3v.pterodactyl.extension;

import lombok.Getter;
import org.gradle.api.Action;
import org.gradle.api.Project;

/**
 * Pterodactyl is an extension that gather Credential (for authentication) and Deploy (build option)
 */
@Getter
public class Pterodactyl {

    private final Project project;

    /**
     * This variable represents a credential object.
     *
     * The Credential object stores information used for authentication or identification purposes.
     * It is marked as private and final, ensuring its value cannot be changed once initialized.
     *
     * Usage example:
     * Credential credential = new Credential();
     */
    private final Credential credential;

    /**
     * Represents a deployment object.
     * <p>
     * The {@code Deploy} class is used to hold information about a deployment.
     * It stores the deployment object as a private final variable, which cannot be changed once set.
     * </p>
     * <p>
     * This class provides access to the deployment object through getter methods, allowing other classes to retrieve the deployment information.
     * </p>
     */
    private final Deploy deploy;

    /**
     * Constructs a new Pterodactyl instance with the given project.
     *
     * @param project the project associated with the Pterodactyl
     */
    public Pterodactyl(Project project) {
        this.project = project;
        this.credential = new Credential();
        this.deploy = new Deploy(project);
    }

    /**
     * Executes the given action with the credential.
     *
     * @param action the action to be executed with the credential
     */
    public void credential(Action<? super Credential> action) {
        action.execute(credential);
    }

    /**
     * Executes the given action with the deploy.
     *
     * @param action the action to be executed with the deploy
     */
    public void deploy(Action<? super Deploy> action) {
        action.execute(deploy);
    }

}
