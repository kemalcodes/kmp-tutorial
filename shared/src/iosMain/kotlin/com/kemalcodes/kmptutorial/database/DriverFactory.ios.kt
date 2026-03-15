// DriverFactory.ios.kt
// iOS-specific implementation of DriverFactory.
//
// On iOS, we use NativeSqliteDriver which uses the SQLite library that
// comes bundled with every iOS device. No extra setup needed.

package com.kemalcodes.kmptutorial.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.kemalcodes.kmptutorial.db.AppDatabase

// Actual implementation for iOS.
// Unlike Android, iOS does not need a Context — SQLite is available directly.
actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        // NativeSqliteDriver uses the iOS file system to store the database.
        // AppDatabase.Schema provides the table definitions for creation/migration.
        return NativeSqliteDriver(AppDatabase.Schema, "AppDatabase.db")
    }
}
