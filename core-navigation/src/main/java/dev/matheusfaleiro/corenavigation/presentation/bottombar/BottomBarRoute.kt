package dev.matheusfaleiro.corenavigation.presentation.bottombar

import androidx.navigation3.runtime.NavKey
import dev.matheusfaleiro.corenavigation.domain.backstack.ApplicationBackStack.RequiresLogin
import kotlinx.serialization.Serializable

/**
 * Top-level routes for bottom bar navigation.
 * Each represents a main section of the app.
 */
sealed class BottomBarRoute : NavKey, RequiresLogin {

    @Serializable
    data object Home : BottomBarRoute()

    @Serializable
    data object Calendar : BottomBarRoute()

    @Serializable
    data object Profile : BottomBarRoute()

    @Serializable
    data object Settings : BottomBarRoute()
}
