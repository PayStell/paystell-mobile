package org.paystell.app.screens.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.paystell.app.ui.components.BackButton
import org.paystell.app.ui.components.EmailInputField
import org.paystell.app.ui.components.PasswordInputField
import org.paystell.app.ui.components.PayStellButton
import org.paystell.app.ui.components.PayStellTextButton
import org.paystell.app.ui.theme.PayStellTheme
import org.paystell.app.ui.theme.StellarBlue
import org.paystell.app.utils.ValidationErrorMessages
import org.paystell.app.utils.isValidEmail

/**
 * Login screen for PayStell application
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onBackClick: () -> Unit
) {
    PayStellTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var rememberMe by remember { mutableStateOf(false) }
        
        var emailError by remember { mutableStateOf("") }
        var passwordError by remember { mutableStateOf("") }
        
        // Mock biometric authentication dialog state
        var showBiometricPrompt by remember { mutableStateOf(false) }
        
        fun validateInputs(): Boolean {
            var isValid = true
            
            if (email.isEmpty()) {
                emailError = ValidationErrorMessages.EMPTY_EMAIL
                isValid = false
            } else if (!isValidEmail(email)) {
                emailError = ValidationErrorMessages.INVALID_EMAIL
                isValid = false
            } else {
                emailError = ""
            }
            
            if (password.isEmpty()) {
                passwordError = ValidationErrorMessages.EMPTY_PASSWORD
                isValid = false
            } else {
                passwordError = ""
            }
            
            return isValid
        }
        
        fun handleLogin() {
            if (validateInputs()) {
                // Mock login success for now
                scope.launch {
                    val message = if (rememberMe) {
                        "Login successful! Your session will be remembered."
                    } else {
                        "Login successful!"
                    }
                    snackbarHostState.showSnackbar(message)
                    onLoginSuccess()
                }
            }
        }

        // Check for existing session if rememberMe was previously used
        LaunchedEffect(Unit) {
            // Simulating check for existing session
            // In a real app, you would check for stored credentials
            val hasExistingSession = false
            if (hasExistingSession) {
                // Auto-login simulation
                scope.launch {
                    snackbarHostState.showSnackbar("Auto-login from saved session")
                    onLoginSuccess()
                }
            }
        }
        
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Login") },
                    navigationIcon = {
                        BackButton(onClick = onBackClick)
                    },
                    backgroundColor = MaterialTheme.colors.surface,
                    elevation = 0.dp
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colors.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(48.dp))
                    
                    Text(
                        text = "Welcome Back",
                        style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Log in to access your account",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(48.dp))
                    
                    EmailInputField(
                        value = email,
                        onValueChange = { 
                            email = it
                            if (emailError.isNotEmpty()) validateInputs()
                        },
                        isError = emailError.isNotEmpty(),
                        errorMessage = emailError
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    PasswordInputField(
                        value = password,
                        onValueChange = { 
                            password = it
                            if (passwordError.isNotEmpty()) validateInputs()
                        },
                        isError = passwordError.isNotEmpty(),
                        errorMessage = passwordError
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Remember me checkbox
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = StellarBlue
                                )
                            )
                            Text(
                                text = "Remember me",
                                style = MaterialTheme.typography.body2
                            )
                        }
                        
                        // Forgot password
                        PayStellTextButton(
                            text = "Forgot Password?",
                            onClick = onForgotPasswordClick
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    PayStellButton(
                        text = "Login",
                        onClick = { handleLogin() }
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Divider(modifier = Modifier.weight(1f))
                        Text(
                            text = " OR ",
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Divider(modifier = Modifier.weight(1f))
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    PayStellButton(
                        text = "Login with Stellar Wallet",
                        onClick = { /* Wallet login implementation */ }
                    )
                    
                    // Add a biometric authentication option
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    PayStellTextButton(
                        text = "Use Biometric Authentication",
                        onClick = { 
                            // In a real app, this would trigger biometric authentication
                            scope.launch {
                                snackbarHostState.showSnackbar("Biometric authentication would be triggered here")
                            }
                        }
                    )
                }
            }
        }
    }
} 