// UserApiClient.kt
// A simple API client that fetches user data from a public REST API.
// All functions are suspend, meaning they run inside coroutines
// without blocking the main thread.

package com.kemalcodes.kmptutorial.network

import com.kemalcodes.kmptutorial.models.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

// The client takes an HttpClient as a parameter.
// This makes it easy to test by passing a mock client.
class UserApiClient(
    private val httpClient: HttpClient = createHttpClient()
) {
    // Base URL for the free JSON placeholder API
    private val baseUrl = "https://jsonplaceholder.typicode.com"

    // Fetches all users from the API.
    // Returns ApiResult.Success with the list, or ApiResult.Error if it fails.
    suspend fun getUsers(): ApiResult<List<User>> {
        return try {
            // Ktor's get() makes an HTTP GET request.
            // The body() call deserializes the JSON response into a list of User objects.
            val users: List<User> = httpClient.get("$baseUrl/users").body()
            ApiResult.Success(users)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Failed to fetch users")
        }
    }

    // Fetches a single user by their ID.
    // The ID is appended to the URL path.
    suspend fun getUserById(id: Int): ApiResult<User> {
        return try {
            val user: User = httpClient.get("$baseUrl/users/$id").body()
            ApiResult.Success(user)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Failed to fetch user with id $id")
        }
    }
}
