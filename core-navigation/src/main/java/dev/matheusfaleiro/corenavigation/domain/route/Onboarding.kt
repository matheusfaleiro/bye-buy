package dev.matheusfaleiro.corenavigation.domain.route

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Onboarding : NavKey {

    @Serializable
    data object Welcome : Onboarding()

    @Serializable
    data object Permissions : Onboarding()

    @Serializable
    data object Final : Onboarding()
}
