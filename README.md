# KMP Tutorial: From Zero to Production

Complete source code for the [KMP Tutorial](https://kemalcodes.com/kmp-tutorial/) series on [kemalcodes.com](https://kemalcodes.com).

A step-by-step Kotlin Multiplatform tutorial that takes you from your first shared function to publishing a real cross-platform app on Android and iOS. 20 tutorials with working code.

## How to Use This Repo

Each tutorial has its own branch. Switch to the branch you want:

```bash
git clone https://github.com/kemalcodes/kmp-tutorial.git
cd kmp-tutorial

# Switch to a specific tutorial
git checkout tutorial-01-what-is-kmp
```

The `main` branch contains the base KMP project. Tutorial branches build on top of it.

## Tutorials

### Part 1: Foundations

| # | Tutorial | Branch | Article |
|---|---------|--------|---------|
| 1 | What is Kotlin Multiplatform? | `tutorial-01-what-is-kmp` | [Read](https://kemalcodes.com/posts/kmp-tutorial-what-is-kotlin-multiplatform/) |
| 2 | Setting Up Your First KMP Project | `tutorial-02-setup` | Coming soon |
| 3 | KMP Project Structure | `tutorial-03-structure` | Coming soon |
| 4 | Compose Multiplatform | `tutorial-04-compose-multiplatform` | Coming soon |
| 5 | KMP vs Flutter vs React Native | `tutorial-05-comparison` | Coming soon |

### Part 2: Core Libraries

| # | Tutorial | Branch | Article |
|---|---------|--------|---------|
| 6 | Ktor Client — Networking | `tutorial-06-ktor` | Coming soon |
| 7 | SQLDelight — Database | `tutorial-07-sqldelight` | Coming soon |
| 8 | DataStore — Storage | `tutorial-08-datastore` | Coming soon |
| 9 | Koin — Dependency Injection | `tutorial-09-koin` | Coming soon |
| 10 | Shared ViewModel | `tutorial-10-viewmodel` | Coming soon |

### Part 3: Architecture & Patterns

| # | Tutorial | Branch | Article |
|---|---------|--------|---------|
| 11 | Clean Architecture | `tutorial-11-architecture` | Coming soon |
| 12 | Navigation | `tutorial-12-navigation` | Coming soon |
| 13 | Testing | `tutorial-13-testing` | Coming soon |
| 14 | Error Handling & Logging | `tutorial-14-error-handling` | Coming soon |

### Part 4: Build a Real App

| # | Tutorial | Branch | Article |
|---|---------|--------|---------|
| 15 | Planning — Notes App | `tutorial-15-app-planning` | Coming soon |
| 16 | Data Layer | `tutorial-16-data-layer` | Coming soon |
| 17 | UI Layer | `tutorial-17-ui-layer` | Coming soon |
| 18 | Publishing | `tutorial-18-publishing` | Coming soon |

### Part 5: Advanced

| # | Tutorial | Branch | Article |
|---|---------|--------|---------|
| 19 | Desktop & Web | `tutorial-19-desktop-web` | Coming soon |
| 20 | Migrating Android to KMP | `tutorial-20-migration` | Coming soon |

## Project Structure

```
kmp-tutorial/
├── shared/                 ← Shared Kotlin code
│   └── src/
│       ├── commonMain/     ← Code for ALL platforms
│       ├── androidMain/    ← Android-specific code
│       └── iosMain/        ← iOS-specific code
├── composeApp/             ← Android/Desktop app (Compose UI)
│   └── src/
│       ├── commonMain/     ← Shared Compose UI
│       ├── androidMain/    ← Android entry point
│       └── iosMain/        ← iOS entry point
├── iosApp/                 ← iOS app (Xcode project)
├── build.gradle.kts
└── settings.gradle.kts
```

## Tech Stack

- **Kotlin Multiplatform** — shared business logic
- **Compose Multiplatform** — shared UI
- **Ktor** — networking
- **SQLDelight** — database
- **Koin** — dependency injection
- **Kotlin Serialization** — JSON parsing

## Requirements

- [Android Studio](https://developer.android.com/studio) (latest stable)
- Xcode (for iOS builds — Mac required)
- JDK 17+

## Run the Project

### Android
```bash
./gradlew :composeApp:assembleDebug
```
Or use the run configuration in Android Studio.

### iOS
Open `iosApp/iosApp.xcodeproj` in Xcode and run on a simulator.

## Blog

All tutorials with detailed explanations at [kemalcodes.com/kmp-tutorial](https://kemalcodes.com/kmp-tutorial/)

## License

[MIT](LICENSE)
