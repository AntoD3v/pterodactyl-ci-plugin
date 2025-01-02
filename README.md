# Pterodactyl CI for Minecraft

This is a simple gradle plugin to help you build and deploy your minecraft server to your pterodactyl panel.

## Usage

### 1. Add the plugin to your buildscript

```groovy

plugins {

    id 'io.github.AntoD3v.pterodactyl-ci-plugin' version '1.2'

}

```

### 2. Generate a new api key

Go to your pterodactyl panel and generate a new api key. Make sure to give it the following permissions:

### 3. Configure the plugin

```groovy
pterodactyl {
    // Required part
    credential {
        apiUrl = "https://your-pterodactyl-app.dev"
        apiKey = "ptlc_xxxxxxxxxxxxxxxxx"
        serverId = "12345678" // 8 first characters of the server uuid
    }
    
    // This part is optional. (Value are auto-computed)
    deploy {
        localBuildOuput = "build/libs/your-plugin.jar" // default: search the most heavy jar in build/libs
        remoteDir = "/plugins" // default: /plugins
        remoteFileName = "my-plugin.jar" // default: project.name-project.version.jar
        commands = ["say Hello World!"] // default: plugman command + broadcast
    }
}
```

**Tips**: Create a `gradle.properties` file in your project root or `~/.gradle/gradle.properties` and add the following line to it:

```properties
pterodactylApiKey=ptlc_XXXXXX
```
Then, you can get this with `project.property("pterodactylApiKey")` in your build.gradle file.

```groovy
...

        apiKey = project.property("pterodactylApiKey")  
...
```


### 4. Create & Run the task

How it works is simple: you build, then deploy

```bash
# Example with native build
./gradlew build deploy

# Example with shadowJar 
./gradlew shadowJar deploy

```

#### Another way
If you want, you could add : 
```bash
tasks.deploy {
    dependsOn("shadowJar")
}
```

The result is you could deploy `./gradlew deploy` (build then, deploy)
