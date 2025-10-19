package dev.matheusfaleiro.featureauthentication.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Authentication feature routes.
 * All routes for the authentication feature are defined here.
 */
sealed class AuthenticationRoute : NavKey {
    @Serializable
    data object Welcome : AuthenticationRoute()

    @Serializable
    data object Login : AuthenticationRoute()

    @Serializable
    data object Register : AuthenticationRoute()

    @Serializable
    data object ForgotPassword : AuthenticationRoute()

    @Serializable
    data object VerifyEmail : AuthenticationRoute()
}
