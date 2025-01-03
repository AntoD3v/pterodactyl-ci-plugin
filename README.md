# Pterodactyl CI Plugin for Minecraft

A Gradle plugin designed to simplify the building and deployment of your Minecraft server to a Pterodactyl panel.

## Features

- Seamless integration with Gradle for Minecraft server deployment.
- Customizable deployment options (output directories, file names, remote commands).
- Simplified configuration using `gradle.properties`.

## Getting Started in 5 minutes !

Follow these steps to integrate the plugin into your project:

### 1. Add the Plugin to Your Build Script

Include the plugin in your `build.gradle.kts` file:
> Gradle Kotlin `build.gradle.kts`
```groovy
plugins {
    id("io.github.AntoD3v.pterodactyl-ci-plugin") version '1.3'
}
```
> Gradle Groovy `build.gradle`
```groovy
plugins {
    id 'io.github.AntoD3v.pterodactyl-ci-plugin' version '1.3'
}
```


### 2. Generate an API Key
Access your Pterodactyl panel and generate a new API key with the necessary permissions.
On your panel, click to `Account Settings` > `Api Credentials` and create you API Key

Open or create your file `~/.gradle/gradle.properties` (recommended) or `<project>/gradle.properties` and put this line :
```
pterodactylApiKey=ptlc_XXXXXX
```

### 3. Configure the Plugin
Choose your gradle version and configure it
> Gradle Kotlin `build.gradle.kts`
```kotlin
pterodactyl {
    credential {
        apiUrl = "https://your-pterodactyl-app.dev" // Url of your pterodactyl panel
        apiKey = project.properties["pterodactylApiKey"].toString() // Previous configured 
        serverId = "12345678" // First 8 characters of your server UUID (Find this into url of server pterodactyl panel)
    }

    deploy{
        localBuildOutput = "build/libs/your-plugin.jar" // Optional: Default is the heaviest jar in build/libs
        remoteDir = "/plugins" // Optional: Default is '/plugins'
        remoteFileName = "your-plugin-${project.version}.jar"  // Optional: Default is '{project.name}-{project.version}.jar'
        commands = listOf(usePlugmanCommand(), "say ✔ Your plugin is deployed !") // Optional: Default includes [plugman reload your-plugin, announcment]
    }
}
```
> Gradle Groovy `build.gradle`
```groovy
pterodactyl {
    credential {
        apiUrl = "https://your-pterodactyl-app.dev"
        apiKey = project.property("pterodactylApiKey")
        serverId = "12345678" // First 8 characters of your server UUID
    }

    deploy {
        localBuildOutput = "build/libs/your-plugin.jar" // Optional: Default is the heaviest jar in build/libs
        remoteDir = "/plugins" // Optional: Default is /plugins
        remoteFileName = "my-plugin.jar" // Optional: Default is '{project.name}-{project.version}.jar'
        commands = [usePlugmanCommand(), "say ✔ Your plugin is deployed !"] // Optional: Default includes [plugman reload your-plugin, announcment]
    }
}
```
#### Utils functions
- `usePlugmanCommand()` resolve the name of plugins by

### 4. Build and Deploy

Per default, task `deploy` will **just upload** the file into build/libs/project-name.
If you want to create a build chain. You could add this code into your `build.gradle.kts` file
Configure your tasks :
> Gradle Kotlin `build.gradle.kts`
```kotlin
tasks.deploy {
    dependsOn("shadowJar")
}
```
> Gradle Groovy `build.gradle`
```groovy
tasks.named("deploy").configure {
    dependsOn("shadowJar")
}
```
(`shadowJar` is just an example, you can replace by `jar`, `build` or anything)

#### Your first deployment !

Just add `deploy` tasks into your IDE or execute `./gradlew deploy`

Well done, you have successfully created a deployment in pterodactyl

## Contributing
Contributions, issues, and feature requests are welcome! Feel free to open an issue or submit a pull request.

## License
This project is licensed under the MIT License.

