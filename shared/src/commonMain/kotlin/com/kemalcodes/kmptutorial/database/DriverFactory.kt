// DriverFactory.kt
// Expect/actual pattern for creating a platform-specific SQLite driver.
//
// SQLDelight needs a SqlDriver to talk to the database. Each platform
// (Android, iOS) uses a different SQLite implementation under the hood,
// so we declare an "expect" class here and provide "actual" implementations
// in androidMain and iosMain.

package com.kemalcodes.kmptutorial.database

import app.cash.sqldelight.db.SqlDriver

// Expect class — each platform must provide its own "actual" implementation.
// This is the KMP way of handling platform-specific code.
expect class DriverFactory {
    fun createDriver(): SqlDriver
}
