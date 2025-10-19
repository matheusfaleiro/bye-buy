package dev.matheusfaleiro.corenavigation.domain.backstack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * A navigation back stack implementation that handles authentication-protected routes.
 *
 * This class manages navigation state while enforcing authentication requirements for specific routes.
 * When a user attempts to navigate to a route that implements [RequiresLogin] without being authenticated,
 * they are automatically redirected to the login route. After successful authentication, the user is
 * navigated to their originally intended destination.
 *
 * Based on the official Navigation 3 conditional navigation recipe.
 *
 * @param T The type of route objects used in navigation
 * @param startRoute The initial route to display when the back stack is created
 * @param loginRoute The route that users are redirected to when attempting to access protected content
 */
class ApplicationBackStack<T : Any>(startRoute: T, private val loginRoute: T) {

    /**
     * Marker interface for routes that require user authentication.
     * Routes implementing this interface will trigger authentication flow when accessed by unauthenticated users.
     */
    interface RequiresLogin

    private var onLoginSuccessRoute: T? = null

    /**
     * Current authentication state of the user.
     * When true, the user can access routes marked with [RequiresLogin].
     */
    var isLoggedIn by mutableStateOf(false)
        private set

    /**
     * The current navigation back stack containing all active routes.
     * This list maintains the navigation history and current state.
     */
    val backStack = mutableStateListOf(startRoute)

    /**
     * Navigates to the specified route.
     *
     * If the route requires authentication (implements [RequiresLogin]) and the user is not logged in,
     * the user will be redirected to the login route and the intended destination will be stored
     * for navigation after successful authentication.
     *
     * @param route The destination route to navigate to
     */
    fun add(route: T) {
        if (route is RequiresLogin && !isLoggedIn) {
            onLoginSuccessRoute = route
            backStack.add(loginRoute)
        } else {
            backStack.add(route)
        }

        if (route == loginRoute) {
            onLoginSuccessRoute = null
        }
    }

    /**
     * Removes the last route from the back stack (back navigation).
     *
     * @return The removed route, or null if the back stack is empty
     */
    fun remove() = backStack.removeLastOrNull()

    /**
     * Marks the user as authenticated and handles post-login navigation.
     *
     * If there was a pending destination (user tried to access a protected route before login),
     * navigates to that destination and removes the login route from the stack.
     */
    fun login() {
        isLoggedIn = true

        onLoginSuccessRoute?.let {
            backStack.add(it)
            backStack.remove(loginRoute)
        }
    }

    /**
     * Logs out the user and removes all authentication-protected routes from the back stack.
     *
     * This ensures that protected content is no longer accessible and the navigation state
     * is cleaned up appropriately.
     */
    fun logout() {
        isLoggedIn = false
        backStack.removeAll { it is RequiresLogin }
    }
}
