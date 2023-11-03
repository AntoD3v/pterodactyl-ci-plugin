package io.github.antod3v.pterodactyl.extension;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the credentials required for accessing an API or server.
 * This class provides getters and setters for the apiUrl, apiKey, and serverId properties.
 */
@Getter
@Setter
public class Credential {

    /**
     * Represents the API URL for making HTTP requests.
     */
    private String apiUrl;

    /**
     * apiKey represents the unique API key associated with a user or application.
     * It is a String variable that holds the value of the API key.
     */
    private String apiKey;

    /**
     * Represents the unique identifier of a server.
     */
    private String serverId;

}