# Bye-Buy 🛍️
[![Build Status](https://app.bitrise.io/app/72fedc78-b56c-45ff-804d-19c02dc14ec1/status.svg?token=Suy3MF4X1EFUCN20v6-u9A&branch=main)](https://app.bitrise.io/app/72fedc78-b56c-45ff-804d-19c02dc14ec1)

An Android application that empowers consumers to make conscious and
informed purchasing decisions when shopping online.

## Overview

Bye-Buy helps users evaluate products thoughtfully before making
purchases, promoting sustainable and mindful consumerism through
data-driven insights and analysis tools.

## Tech Stack

### Architecture
- **MVVM** (Model-View-ViewModel)
- **Clean Architecture** (Domain, Data, Presentation layers)
- **Jetpack Components** (ViewModel, Navigation)
- **Jetpack Compose** - Modern UI toolkit

### Language
- **Kotlin** - 100%

### Dependency Injection
- **Dagger** - Compile-time DI framework

### Database
- **Room** - Local persistence layer

### Networking
- **Retrofit** - REST API client

### CI/CD
- **Bitrise** - Continuous integration and delivery

### Additional Libraries
- **Coroutines & Flow** - Asynchronous programming
- **Navigation Component** - In-app navigation
- **ViewBinding** - View interaction
- **Coil** - Image loading
- **MockK** - Unit testing
- **Espresso** - UI testing
- **Timber** - Logging

## Getting Started

### Prerequisites
- Android Studio Hedgehog (or later)
- JDK 17
- Android SDK 30
- Gradle 8.0+

### Setup
1. Clone the repository

       git clone https://github.com/your-org/bye-buy.git

2. Open project in Android Studio

3. Sync Gradle files

4. Create `local.properties` file with your API keys:

       API_KEY=your_api_key_here

5. Build and run

## Building

    # Debug build
    ./gradlew assembleDebug

    # Release build
    ./gradlew assembleRelease

    # Run tests
    ./gradlew test

    # Run instrumented tests
    ./gradlew connectedAndroidTest

## Code Style

This project follows the Kotlin Coding Conventions and uses ktlint
for code formatting.

    # Check code style
    ./gradlew ktlintCheck

    # Format code
    ./gradlew ktlintFormat

## Testing

- **Unit Tests**: Domain and data layer logic
- **Integration Tests**: Repository and API interactions
- **UI Tests**: User interface flows with Espresso

## Contributing

1. Create a feature branch from `main`
2. Follow commit message conventions
3. Write tests for new features
4. Submit a pull request

### Commit Message Format
Follow the Git commit rules for consistent commit messages.

## CI/CD Pipeline
Bitrise automatically:
- Runs unit and UI tests
- Performs code quality checks
- Builds release APK/AAB
- Deploys to internal testing track

**Status**: 🚧 Under Development