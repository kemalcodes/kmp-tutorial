// ApiResult.kt
// A sealed class that wraps API responses.
// This pattern makes it easy to handle success and error states
// in a type-safe way, without throwing exceptions everywhere.

package com.kemalcodes.kmptutorial.network

// Sealed classes restrict which subclasses can exist.
// Here, an API call can only be a Success or an Error.
sealed class ApiResult<out T> {

    // Holds the data when the request succeeds
    data class Success<T>(val data: T) : ApiResult<T>()

    // Holds the error message when something goes wrong
    data class Error(val message: String) : ApiResult<Nothing>()
}
