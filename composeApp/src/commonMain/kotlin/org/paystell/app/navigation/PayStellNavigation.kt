package org.paystell.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.paystell.app.screens.authentication.ForgotPasswordScreen
import org.paystell.app.screens.authentication.LoginScreen
import org.paystell.app.screens.authentication.SignUpScreen
import org.paystell.app.screens.home.HomeScreen
import org.paystell.app.screens.welcome.WelcomeScreen

/**
 * Routes for the PayStell application
 */
object PayStellDestinations {
    const val WELCOME_ROUTE = "welcome"
    const val LOGIN_ROUTE = "login"
    const val SIGNUP_ROUTE = "signup"
    const val FORGOT_PASSWORD_ROUTE = "forgot_password"
    const val HOME_ROUTE = "home"
}

/**
 * Main navigation component for the PayStell application
 * Simple implementation that doesn't rely on Navigation Compose
 * This is a temporary implementation until the dependencies are properly resolved
 */
@Composable
fun PayStellNavigation(
    startDestination: String = PayStellDestinations.WELCOME_ROUTE
) {
    var currentRoute by remember { mutableStateOf(startDestination) }
    var backStack by remember { mutableStateOf(listOf<String>()) }
    
    fun navigate(route: String) {
        backStack = backStack + currentRoute
        currentRoute = route
    }
    
    fun popBackStack() {
        if (backStack.isNotEmpty()) {
            currentRoute = backStack.last()
            backStack = backStack.dropLast(1)
        }
    }
    
    fun navigateWithPopUp(route: String, popUpTo: String, inclusive: Boolean = false) {
        val index = backStack.indexOf(popUpTo)
        if (index != -1) {
            backStack = if (inclusive) {
                backStack.subList(0, index)
            } else {
                backStack.subList(0, index + 1)
            }
        }
        currentRoute = route
    }
    
    when (currentRoute) {
        PayStellDestinations.WELCOME_ROUTE -> {
            WelcomeScreen(
                onGetStartedClick = { navigate(PayStellDestinations.SIGNUP_ROUTE) },
                onLoginClick = { navigate(PayStellDestinations.LOGIN_ROUTE) }
            )
        }
        PayStellDestinations.LOGIN_ROUTE -> {
            LoginScreen(
                onLoginSuccess = { 
                    navigateWithPopUp(
                        PayStellDestinations.HOME_ROUTE,
                        PayStellDestinations.WELCOME_ROUTE,
                        true
                    )
                },
                onForgotPasswordClick = { navigate(PayStellDestinations.FORGOT_PASSWORD_ROUTE) },
                onBackClick = { popBackStack() }
            )
        }
        PayStellDestinations.SIGNUP_ROUTE -> {
            SignUpScreen(
                onSignUpSuccess = { 
                    navigateWithPopUp(
                        PayStellDestinations.HOME_ROUTE,
                        PayStellDestinations.WELCOME_ROUTE,
                        true
                    )
                },
                onBackClick = { popBackStack() }
            )
        }
        PayStellDestinations.FORGOT_PASSWORD_ROUTE -> {
            ForgotPasswordScreen(
                onResetRequestSent = { popBackStack() },
                onBackClick = { popBackStack() }
            )
        }
        PayStellDestinations.HOME_ROUTE -> {
            HomeScreen()
        }
    }
} 