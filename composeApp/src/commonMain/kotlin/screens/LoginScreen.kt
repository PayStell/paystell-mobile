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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import components.EmailTextField
import components.PasswordTextField
import components.PrimaryButton
import components.SecondaryButton
import components.TextButton
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import navigation.Screen
import viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigator: Navigator,
    viewModel: AuthViewModel = remember { AuthViewModel() }
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(viewModel.isSuccess) {
        if (viewModel.isSuccess) {
            snackbarHostState.showSnackbar("Login successful!")
            navigator.navigate(Screen.Home.route)
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
                title = { Text("Login") },
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
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            EmailTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                isError = viewModel.emailError.isNotEmpty(),
                errorMessage = viewModel.emailError,
                onImeAction = { viewModel.validateEmail() }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            PasswordTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                isError = viewModel.passwordError.isNotEmpty(),
                errorMessage = viewModel.passwordError,
                onImeAction = { viewModel.validatePassword() }
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            TextButton(
                text = "Forgot Password?",
                onClick = { navigator.navigate(Screen.ForgotPassword.route) },
                modifier = Modifier.align(Alignment.End)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            if (viewModel.isLoading) {
                CircularProgressIndicator()
            } else {
                PrimaryButton(
                    text = "Login",
                    onClick = {
                        scope.launch {
                            viewModel.login()
                        }
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                SecondaryButton(
                    text = "Login with Wallet",
                    onClick = {
                        // Wallet login functionality would be implemented here
                        scope.launch {
                            snackbarHostState.showSnackbar("Wallet login not implemented yet")
                        }
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            TextButton(
                text = "Don't have an account? Sign up",
                onClick = { navigator.navigate(Screen.SignUp.route) }
            )
        }
    }
}

