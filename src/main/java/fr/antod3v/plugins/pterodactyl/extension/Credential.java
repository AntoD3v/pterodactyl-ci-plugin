package fr.antod3v.plugins.pterodactyl.extension;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Credential {

    private String apiUrl;
    private String apiKey;
    private String serverId;

}