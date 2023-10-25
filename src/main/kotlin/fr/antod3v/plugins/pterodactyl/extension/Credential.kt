package fr.antod3v.plugins.pterodactyl.extension

open class Credential(
    var apiUrl: String,
    var apiKey: String,
    var serverId: String? = null,
)