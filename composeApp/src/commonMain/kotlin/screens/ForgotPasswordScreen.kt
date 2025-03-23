package screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.EmailTextField
import components.PrimaryButton
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import navigation.Screen
import viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    navigator: Navigator,
    viewModel: AuthViewModel = remember { AuthViewModel() }
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(viewModel.isSuccess) {
        if (viewModel.isSuccess) {
            snackbarHostState.showSnackbar("Password reset email sent!")
            navigator.navigate(Screen.Login.route)
        }
    }
    
    LaunchedEffect(viewModel.errorMessage) {
        if (viewModel.errorMessage.isNotEmpty()) {
            snackbarHostState.showSnackbar(viewModel.errorMessage)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Forgot Password") },
                navigationIcon = {
                    IconButton(onClick = { navigator.goBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Reset Your Password",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Enter your email address and we'll send you instructions to reset your password",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            EmailTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                isError = viewModel.emailError.isNotEmpty(),
                errorMessage = viewModel.emailError,
                onImeAction = { viewModel.validateEmail() }
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            if (viewModel.isLoading) {
                CircularProgressIndicator()
            } else {
                PrimaryButton(
                    text = "Reset Password",
                    onClick = {
                        scope.launch {
                            viewModel.resetPassword()
                        }
                    }
                )
            }
        }
    }
}

