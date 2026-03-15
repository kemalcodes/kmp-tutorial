// DriverFactory.android.kt
// Android-specific implementation of DriverFactory.
//
// On Android, we use AndroidSqliteDriver which wraps Android's built-in
// SQLite support. It needs a Context to know where to store the database file.

package com.kemalcodes.kmptutorial.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.kemalcodes.kmptutorial.db.AppDatabase

// Actual implementation for Android.
// We pass in the Android Context so the driver knows where to save the .db file.
actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        // "AppDatabase.db" is the filename for the SQLite database on disk.
        // AppDatabase.Schema tells the driver how to create/migrate tables.
        return AndroidSqliteDriver(AppDatabase.Schema, context, "AppDatabase.db")
    }
}
