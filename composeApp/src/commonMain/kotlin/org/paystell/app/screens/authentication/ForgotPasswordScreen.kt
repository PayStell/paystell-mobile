package org.paystell.app.screens.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import org.paystell.app.ui.components.BackButton
import org.paystell.app.ui.components.EmailInputField
import org.paystell.app.ui.components.PayStellButton
import org.paystell.app.ui.theme.PayStellTheme
import org.paystell.app.ui.theme.SuccessGreen
import org.paystell.app.utils.ValidationErrorMessages
import org.paystell.app.utils.isValidEmail

/**
 * Forgot Password screen for PayStell application
 */
@Composable
fun ForgotPasswordScreen(
    onResetRequestSent: () -> Unit,
    onBackClick: () -> Unit
) {
    PayStellTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        
        var email by remember { mutableStateOf("") }
        var emailError by remember { mutableStateOf("") }
        
        // UI states
        var isLoading by remember { mutableStateOf(false) }
        var showNetworkError by remember { mutableStateOf(false) }
        var showUnknownEmailError by remember { mutableStateOf(false) }
        var showSuccessDialog by remember { mutableStateOf(false) }
        
        fun validateInputs(): Boolean {
            val trimmedEmail = email.trim()
            if (trimmedEmail.isEmpty()) {
                emailError = ValidationErrorMessages.EMPTY_EMAIL
                return false
            } else if (!isValidEmail(trimmedEmail)) {
                emailError = ValidationErrorMessages.INVALID_EMAIL
                return false
            } else if (trimmedEmail == "unknown@example.com") {
                // Mock check for unknown email
                showUnknownEmailError = true
                return false
            }

            emailError = ""
            return true
        }
        
        fun handleResetRequest() {
            if (validateInputs()) {
                isLoading = true
                
                // Simulate network request
                scope.launch {
                    try {
                        // Simulate network delay
                        delay(1500)

                        // Simulate random network error (20% chance)
                        if (Random.nextDouble() < 0.2) {
                            throw Exception("Network error")
                        }

                        // Success path
                        showSuccessDialog = true
                        delay(3000) // Show success message briefly
                        onResetRequestSent()
                    } catch (e: Exception) {
                        // Show network error
                        showNetworkError = true
                    } finally {
                        isLoading = false
                    }
                }
            }
        }
        
        if (showNetworkError) {
            AlertDialog(
                onDismissRequest = { showNetworkError = false },
                title = { Text("Connection Error") },
                text = { Text("We encountered a problem connecting to our servers. Please check your internet connection and try again.") },
                confirmButton = {
                    Button(onClick = { showNetworkError = false }) {
                        Text("OK")
                    }
                }
            )
        }
        
        if (showUnknownEmailError) {
            AlertDialog(
                onDismissRequest = { showUnknownEmailError = false },
                title = { Text("Email Not Found") },
                text = { Text("We couldn't find an account associated with $email. Please check if you entered the correct email address.") },
                confirmButton = {
                    Button(onClick = { showUnknownEmailError = false }) {
                        Text("OK")
                    }
                }
            )
        }
        
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { },
                title = { 
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Success",
                            tint = SuccessGreen,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text("Reset Link Sent")
                    }
                },
                text = { 
                    Text(
                        "We've sent a password reset link to $email. Please check your inbox and follow the instructions.",
                        textAlign = TextAlign.Center
                    )
                },
                confirmButton = { }
            )
        }
        
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Forgot Password") },
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
                    // Loading indicator
                    if (isLoading) {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colors.primary
                        )
                    } else {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    
                    Spacer(modifier = Modifier.height(48.dp))
                    
                    Text(
                        text = "Reset Your Password",
                        style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Enter your email address and we'll send you a link to reset your password",
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
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    PayStellButton(
                        text = "Send Reset Link",
                        onClick = { handleResetRequest() },
                        enabled = !isLoading
                    )
                    
                    if (!isLoading) {
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        TextButton(onClick = onBackClick) {
                            Text("Back to Login")
                        }
                    }
                }
            }
        }
    }
} 