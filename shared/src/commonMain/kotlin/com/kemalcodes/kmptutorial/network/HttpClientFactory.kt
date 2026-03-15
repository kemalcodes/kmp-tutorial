// HttpClientFactory.kt
// Creates a configured Ktor HttpClient that works on all platforms.
// Ktor uses a plugin system — here we install ContentNegotiation
// so the client can automatically serialize/deserialize JSON.

package com.kemalcodes.kmptutorial.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// This function returns an HttpClient with JSON support.
// Each platform (Android/iOS) provides its own engine automatically
// through the expect/actual mechanism or dependency injection.
fun createHttpClient(): HttpClient {
    return HttpClient {
        // Install the ContentNegotiation plugin for JSON handling
        install(ContentNegotiation) {
            json(
                Json {
                    // Don't crash if the API returns fields we didn't define
                    ignoreUnknownKeys = true

                    // Make the JSON output easier to read when debugging
                    prettyPrint = true
                }
            )
        }
    }
}
