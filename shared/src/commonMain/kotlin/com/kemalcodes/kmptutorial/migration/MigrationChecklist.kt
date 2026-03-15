package com.kemalcodes.kmptutorial.migration

/**
 * Migration checklist for moving an existing Android app to KMP.
 *
 * This file serves as a reference for readers following KMP Tutorial #20.
 * It documents the typical migration steps and what to evaluate before
 * starting the migration process.
 *
 * Step 1: Assess what to share
 *   - Business logic (use cases, validation, formatting)
 *   - Networking (Retrofit → Ktor)
 *   - Database (Room → SQLDelight)
 *   - Preferences (SharedPreferences → DataStore KMP)
 *
 * Step 2: What stays native
 *   - UI layer (Compose stays on Android, SwiftUI on iOS)
 *   - Platform SDKs (camera, biometrics, push notifications)
 *   - Third-party Android-only SDKs (Firebase, Play Services)
 *
 * Step 3: Migration order
 *   1. Create shared module with commonMain, androidMain, iosMain
 *   2. Move domain models to commonMain
 *   3. Move networking to Ktor in commonMain
 *   4. Move database to SQLDelight in commonMain
 *   5. Move business logic / use cases to commonMain
 *   6. Create iOS app consuming the shared module
 *
 * Step 4: Gradual adoption
 *   - Keep the existing Android app working at every step
 *   - Move one layer at a time, not everything at once
 *   - Use expect/actual for platform-specific needs
 */
object MigrationChecklist {
    val steps = listOf(
        "Assess shareable code vs platform-specific code",
        "Create shared module structure",
        "Move domain models to commonMain",
        "Replace Retrofit with Ktor in commonMain",
        "Replace Room with SQLDelight in commonMain",
        "Move business logic to commonMain",
        "Create iOS app with SwiftUI consuming shared module",
        "Set up CI/CD for both platforms"
    )
}
