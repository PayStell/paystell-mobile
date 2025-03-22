package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import screens.ForgotPasswordScreen
import screens.LoginScreen
import screens.SignUpScreen
import screens.WelcomeScreen

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object Home : Screen("home")
}

@Composable
fun NavGraph() {
    val navigator = rememberNavigator()
    
    NavHost(
        navigator = navigator,
        initialRoute = Screen.Welcome.route
    ) {
        scene(route = Screen.Welcome.route) {
            WelcomeScreen(navigator = navigator)
        }
        
        scene(route = Screen.Login.route) {
            LoginScreen(navigator = navigator)
        }
        
        scene(route = Screen.SignUp.route) {
            SignUpScreen(navigator = navigator)
        }
        
        scene(route = Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navigator = navigator)
        }
        
        // Home screen would be implemented later
        scene(route = Screen.Home.route) {
            // HomeScreen(navigator = navigator)
        }
    }
}

