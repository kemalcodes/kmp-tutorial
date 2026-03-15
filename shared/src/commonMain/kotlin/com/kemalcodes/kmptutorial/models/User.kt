// User.kt
// A data model that represents a user from the API.
// The @Serializable annotation lets kotlinx-serialization
// convert JSON into this Kotlin object automatically.

package com.kemalcodes.kmptutorial.models

import kotlinx.serialization.Serializable

// Each field maps to a key in the JSON response.
// If a JSON key has a different name, you can use @SerialName("json_key").
@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String
)
