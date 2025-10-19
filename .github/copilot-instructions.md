# Copilot Instructions for Bye-Buy Android Project

## Architecture Overview

This is a **modular Android app** using **Clean Architecture** with **MVVM pattern** and **Jetpack Compose**. The project promotes conscious consumer decision-making through data-driven insights.

### Module Structure
- `app/` - Main application module, entry point with `ByeBuyActivity`
- `core-navigation/` - Centralized navigation using **Navigation 3** (experimental)
- `feature-*` modules - Feature-specific modules (`authentication`, `onboarding`, `home`)

### Feature Module Anatomy (Clean Architecture)

Each `feature-*` module follows Clean Architecture principles, separating concerns into three main layers:

-   **`domain`**: Pure Kotlin module.
    -   **Use Cases**: Encapsulates a single piece of business logic (e.g., `GetUserProfileUseCase`). Should have a single public `invoke` function.
    -   **Models**: Defines the core business models (entities).
    -   **Repository Interfaces**: Defines the contracts for data access, to be implemented by the `data` layer.

-   **`data`**: Android module that depends on the `domain` layer.
    -   **Repositories**: Implements the repository interfaces from the `domain` layer.
    -   **Data Sources**: Manages data from remote (Retrofit APIs) and/or local (Room database) sources.
    -   **DTOs**: Data Transfer Objects for network or database operations. Maps DTOs to/from domain models.

-   **`presentation`**: Android module that depends on the `domain` layer.
    -   **ViewModels**: Implements the MVVM pattern. Executes use cases and exposes state to the UI.
    -   **UI (Compose)**: Composable functions that observe state from the ViewModel and render the UI.
    -   **Navigation**: UI-level navigation events are sent to the ViewModel, which then communicates with the `core-navigation` module.

**Example Package Structure for a feature:**
```
feature-authentication/
├─ src/main/java/dev/matheusfaleiro/feature_authentication/
│  ├─ domain/
│  │  ├─ model/User.kt
│  │  ├─ repository/AuthRepository.kt
│  │  └─ usecase/LoginUseCase.kt
│  ├─ data/
│  │  ├─ repository/AuthRepositoryImpl.kt
│  │  └─ remote/dto/LoginRequest.kt
│  └─ presentation/
│     ├─ viewmodel/LoginViewModel.kt
│     └─ ui/LoginScreen.kt
```

### Key Architectural Patterns

**Navigation 3 with Type-Safe Routes**: Uses sealed classes for navigation destinations
```kotlin
// Example from core-navigation/domain/route/Onboarding.kt
sealed class Onboarding : NavKey {
    @Serializable
    data object Welcome : Onboarding()
    @Serializable 
    data object Permissions : Onboarding()
}
```

**Single Activity Architecture**: All UI flows through `ByeBuyActivity` → `NavigationDisplay`
- Main activity delegates to `NavigationDisplay` from `core-navigation`
- Uses `NavDisplay` with `rememberNavBackStack` for navigation state management

## Development Workflows

### Build Commands
```bash
./gradlew assembleDebug        # Debug build
./gradlew assembleRelease      # Release build
./gradlew test                 # Unit tests
./gradlew connectedAndroidTest # Instrumented tests
```

### Creating a New Feature

1.  **Create New Modules**: Create a new Android Library module for `feature-<feature_name>`, and if you are following a strict approach, a pure Kotlin/Java Library for its `domain` layer.
2.  **Register Module**: Add the new module(s) to `settings.gradle.kts` using type-safe project accessors.
3.  **Define Layers**: Set up the `domain`, `data` and `presentation` package structure inside the new feature module.
4.  **Define Navigation**:
    -   Create feature-specific routes in `feature-<name>/presentation/navigation/<Feature>Routes.kt`
    -   Create feature navigation graph in `feature-<name>/presentation/navigation/<Feature>NavigationGraph.kt`
    -   Register the feature graph in main `NavigationGraph.kt` in `core-navigation`
5.  **Implement the Feature**:
    -   **Domain**: Create use cases, models, and repository interfaces in the domain module.
    -   **Data**: Implement the repository to fetch/store data in the data layer of the feature module.
    -   **Presentation**: Build the ViewModel and Compose UI screens in the presentation layer of the feature module.
6.  **Add Dependencies**: In the feature module `build.gradle.kts`, add an `implementation` dependency on its `domain` module, and on the `core-navigation` module. The `app` module will get an `implementation` dependency on the new feature module.

### Code Quality
```bash
./gradlew ktlintCheck    # Lint checking
./gradlew ktlintFormat   # Auto-formatting
./gradlew detekt         # Static analysis
```

**Detekt Configuration**: Custom rules in `config/detekt/detekt.yml` with Compose-specific rules enabled.

## Project-Specific Conventions

### Dependencies Management
- **Version Catalog**: All versions centralized in `gradle/libs.versions.toml`
- **Type-Safe Project Accessors**: Use `projects.coreNavigation` instead of `":core-navigation"`
- **Dependency Bundles**: Use bundles like `libs.bundles.androidx.navigation` for related dependencies

### Package Structure
Follow domain-based packaging: `dev.matheusfaleiro.[module]/[layer]/[feature]`
- Example: `dev.matheusfaleiro.corenavigation.presentation.graph`

### Gradle Configuration
- **JVM Target**: 21 (defined in version catalog)
- **Min SDK**: 30, **Target SDK**: 36
- **Compile SDK**: 36 (using `release()` function)

### Compose Integration
- Uses **Material 3** with adaptive navigation
- **Navigation 3** with ViewModel integration via `rememberViewModelStoreNavEntryDecorator()`
- State management through `rememberSaveableStateHolderNavEntryDecorator()`

## Key Files & Patterns

### Essential Configuration Files
- `settings.gradle.kts` - Module registration and type-safe accessors
- `gradle/libs.versions.toml` - Centralized dependency management
- `local.properties` - API keys (create manually, not in VCS)

### Navigation Implementation
- **Feature-Level Navigation**: Each feature module has its own navigation graph and routes
- **Route Definition**: Routes defined as sealed classes within each feature's `navigation/` package
- **Core Navigation**: `core-navigation` provides navigation contracts and shared navigation utilities
- **Main Graph**: `core-navigation/presentation/graph/NavigationGraph.kt` composes all feature graphs
- Uses `entryProvider` for destination composables within each feature

**Example Feature Navigation Structure:**
```
feature-authentication/
└─ presentation/
   ├─ navigation/
   │  ├─ AuthRoutes.kt          # Feature-specific routes
   │  └─ AuthNavigationGraph.kt # Feature's navigation graph
   └─ ui/LoginScreen.kt
```

### Module Dependencies
- **Feature Independence**: Feature modules are completely independent (no cross-feature dependencies)
- **Navigation Contracts**: All modules depend on `core-navigation` for navigation contracts only
- **App Orchestration**: App module orchestrates feature modules through navigation composition
- **Cyclic Dependency Prevention**: Features communicate through `core-navigation` contracts, never directly

## CI/CD & Quality

**Bitrise Integration**: Automated builds, tests, and deployments configured
- Runs on every PR and main branch push
- Includes code quality checks (ktlint, detekt)
- Deploys to internal testing track

## Important Notes

- **Navigation 3** is experimental - expect API changes
- No traditional DI framework setup yet (Dagger mentioned in README but not implemented)
- Feature modules are prepared but largely empty (development in progress)
- Uses edge-to-edge display with `enableEdgeToEdge()` in main activity
