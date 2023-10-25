package fr.antod3v.plugins.pterodactyl.extension

data class Credential(
    var apiUrl: String,
    var apiKey: String,
    var serverId: String? = null,
)