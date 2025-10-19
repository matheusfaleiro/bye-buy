package dev.matheusfaleiro.featureauthentication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider

/**
 * Authentication feature navigation entries.
 * Simple composable function that provides navigation entries for auth screens.
 */
@Composable
fun AuthenticationNavigationGraph() = entryProvider {
    entry<AuthenticationRoute.Login> {
    }

    entry<AuthenticationRoute.Register> {
    }

    entry<AuthenticationRoute.ForgotPassword> {
    }

    entry<AuthenticationRoute.VerifyEmail> { verifyEmailRoute ->
    }
}
