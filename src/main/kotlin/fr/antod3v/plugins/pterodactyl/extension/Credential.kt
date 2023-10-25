package fr.antod3v.plugins.pterodactyl.extension

data class Credential(
    val apiUrl: String,
    val apiKey: String,
    val serverId: String? = null,
)