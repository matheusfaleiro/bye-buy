package dev.matheusfaleiro.corenavigation.presentation.graph

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.matheusfaleiro.corenavigation.domain.backstack.ApplicationBackStack
import dev.matheusfaleiro.corenavigation.presentation.bottombar.BottomBarRoute
import dev.matheusfaleiro.featureauthentication.presentation.navigation.AuthenticationRoute

@Composable
fun ByeBuyNavigationGraph(modifier: Modifier = Modifier) {
    val isUserAlreadyLoggedIn = remember { false }

    val startRoute = if (isUserAlreadyLoggedIn) {
        BottomBarRoute.Home
    } else {
        AuthenticationRoute.Login
    }

    val applicationBackStack = remember {
        ApplicationBackStack(
            startRoute = startRoute,
            loginRoute = AuthenticationRoute.Login
        )
    }

    NavDisplay(
        backStack = applicationBackStack.backStack,
        onBack = { applicationBackStack.remove() },
        modifier = modifier,
        entryProvider = entryProvider {
            entry<AuthenticationRoute.Login> {
                Column {
                    Text("🔒 Please Login to Continue")
                    Text("Logged in: ${applicationBackStack.isLoggedIn}")
                    Button(onClick = {
                        // Simulate login process
                        applicationBackStack.login()
                        // Navigate to home after login
                        applicationBackStack.add(BottomBarRoute.Home)
                    }) {
                        Text("Login")
                    }
                    Button(onClick = {
                        applicationBackStack.add(AuthenticationRoute.Register)
                    }) {
                        Text("Register")
                    }
                }
            }

            entry<AuthenticationRoute.Register> {
                Column {
                    Text("📝 Create Account")
                    Button(onClick = {
                        // Simulate registration + login
                        applicationBackStack.login()
                        applicationBackStack.add(BottomBarRoute.Home)
                    }) {
                        Text("Complete Registration")
                    }
                }
            }

            entry<BottomBarRoute.Home> {
                Column {
                    Text("🏠 Home Screen")
                    Text("Welcome! You're logged in: ${applicationBackStack.isLoggedIn}")

                    Button(onClick = { applicationBackStack.add(BottomBarRoute.Calendar) }) {
                        Text("📅 Calendar (requires login)")
                    }
                    Button(onClick = { applicationBackStack.add(BottomBarRoute.Profile) }) {
                        Text("👤 Profile (requires login)")
                    }
                    Button(onClick = { applicationBackStack.add(BottomBarRoute.Settings) }) {
                        Text("⚙️ Settings")
                    }

                    Button(onClick = {
                        applicationBackStack.logout()
                    }) {
                        Text("🚪 Logout")
                    }
                }
            }

            entry<BottomBarRoute.Calendar> {
                Column {
                    Text("📅 Calendar Feature")
                    Text("This screen requires authentication")
                    Button(onClick = { applicationBackStack.logout() }) {
                        Text("Logout")
                    }
                }
            }

            entry<BottomBarRoute.Profile> {
                Column {
                    Text("👤 Profile Feature")
                    Text("This screen requires authentication")
                    Button(onClick = { applicationBackStack.logout() }) {
                        Text("Logout")
                    }
                }
            }

            entry<BottomBarRoute.Settings> {
                Column {
                    Text("⚙️ Settings Feature")
                    Text("This screen is accessible without login")
                    if (applicationBackStack.isLoggedIn) {
                        Button(onClick = { applicationBackStack.logout() }) {
                            Text("Logout")
                        }
                    } else {
                        Button(onClick = { applicationBackStack.add(AuthenticationRoute.Login) }) {
                            Text("Login")
                        }
                    }
                }
            }
        }
    )
}
