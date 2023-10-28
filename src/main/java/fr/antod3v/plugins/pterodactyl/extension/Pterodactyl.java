package fr.antod3v.plugins.pterodactyl.extension;

import lombok.Getter;
import lombok.Setter;
import org.gradle.api.Action;
import org.gradle.api.Project;

@Getter
public class Pterodactyl {

    private final Project project;

    private final Credential credential;

    private final Deploy deploy;

    public Pterodactyl(Project project) {
        this.project = project;
        this.credential = new Credential();
        this.deploy = new Deploy(project);
    }

    public void credential(Action<? super Credential> action) {
        action.execute(credential);
    }

    public void deploy(Action<? super Deploy> action) {
        action.execute(deploy);
    }

}
