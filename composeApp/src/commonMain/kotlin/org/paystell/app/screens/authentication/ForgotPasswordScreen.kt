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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.paystell.app.ui.components.BackButton
import org.paystell.app.ui.components.EmailInputField
import org.paystell.app.ui.components.PayStellButton
import org.paystell.app.ui.theme.PayStellTheme
import org.paystell.app.ui.theme.SuccessGreen

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
        
        // Use ViewModel for state management
        val viewModel = remember { ForgotPasswordViewModel() }
        
        // Collect all states from the ViewModel
        val email by viewModel.email.collectAsState()
        val emailError by viewModel.emailError.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val showNetworkError by viewModel.showNetworkError.collectAsState()
        val showUnknownEmailError by viewModel.showUnknownEmailError.collectAsState()
        val showSuccessDialog by viewModel.showSuccessDialog.collectAsState()
        
        if (showNetworkError) {
            AlertDialog(
                onDismissRequest = { viewModel.dismissNetworkError() },
                title = { Text("Connection Error") },
                text = { Text("We encountered a problem connecting to our servers. Please check your internet connection and try again.") },
                confirmButton = {
                    Button(onClick = { viewModel.dismissNetworkError() }) {
                        Text("OK")
                    }
                }
            )
        }
        
        if (showUnknownEmailError) {
            AlertDialog(
                onDismissRequest = { viewModel.dismissUnknownEmailError() },
                title = { Text("Email Not Found") },
                text = { Text("We couldn't find an account associated with $email. Please check if you entered the correct email address.") },
                confirmButton = {
                    Button(onClick = { viewModel.dismissUnknownEmailError() }) {
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
                        onValueChange = { viewModel.updateEmail(it) },
                        isError = emailError.isNotEmpty(),
                        errorMessage = emailError
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    PayStellButton(
                        text = "Send Reset Link",
                        onClick = { viewModel.submitResetRequest(onResetRequestSent) },
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