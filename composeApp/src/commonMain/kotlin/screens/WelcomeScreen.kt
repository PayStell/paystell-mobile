package screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.PayStellLogo
import components.PrimaryButton
import components.TextButton
import moe.tlaster.precompose.navigation.Navigator
import navigation.Screen

@Composable
fun WelcomeScreen(navigator: Navigator) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PayStellLogo()
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Welcome to PayStell",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Your secure payment solution for the Stellar network",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        PrimaryButton(
            text = "Get Started",
            onClick = { navigator.navigate(Screen.SignUp.route) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(
            text = "I already have an account",
            onClick = { navigator.navigate(Screen.Login.route) }
        )
    }
}

