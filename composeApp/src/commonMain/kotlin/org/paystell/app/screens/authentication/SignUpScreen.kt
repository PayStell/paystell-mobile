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
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import org.paystell.app.ui.components.BackButton
import org.paystell.app.ui.components.EmailInputField
import org.paystell.app.ui.components.PasswordInputField
import org.paystell.app.ui.components.PayStellButton
import org.paystell.app.ui.theme.ErrorRed
import org.paystell.app.ui.theme.PayStellTheme
import org.paystell.app.ui.theme.SuccessGreen
import org.paystell.app.utils.ValidationErrorMessages
import org.paystell.app.utils.doPasswordsMatch
import org.paystell.app.utils.isValidEmail
import org.paystell.app.utils.isValidPassword

/**
 * Sign Up screen for PayStell application
 */
@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    PayStellTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        
        var emailError by remember { mutableStateOf("") }
        var passwordError by remember { mutableStateOf("") }
        var confirmPasswordError by remember { mutableStateOf("") }
        
        // UI states
        var isLoading by remember { mutableStateOf(false) }
        var showNetworkError by remember { mutableStateOf(false) }
        var showExistingEmailError by remember { mutableStateOf(false) }
        var showSuccessDialog by remember { mutableStateOf(false) }
        
        fun validateInputs(): Boolean {
            var isValid = true
            
            val trimmedEmail = email.trim()
            if (trimmedEmail.isEmpty()) {
                emailError = ValidationErrorMessages.EMPTY_EMAIL
                isValid = false
            } else if (!isValidEmail(trimmedEmail)) {
                emailError = ValidationErrorMessages.INVALID_EMAIL
                isValid = false
            } else {
                emailError = ""
                
                // Check for existing email (mock)
                if (trimmedEmail == "existing@example.com") {
                    showExistingEmailError = true
                    isValid = false
                }
            }
            
            if (password.isEmpty()) {
                passwordError = ValidationErrorMessages.EMPTY_PASSWORD
                isValid = false
            } else if (!isValidPassword(password)) {
                passwordError = ValidationErrorMessages.INVALID_PASSWORD
                isValid = false
            } else {
                passwordError = ""
            }
            
            if (confirmPassword.isEmpty()) {
                confirmPasswordError = ValidationErrorMessages.EMPTY_PASSWORD
                isValid = false
            } else if (!doPasswordsMatch(password, confirmPassword)) {
                confirmPasswordError = ValidationErrorMessages.PASSWORDS_DONT_MATCH
                isValid = false
            } else {
                confirmPasswordError = ""
            }
            
            return isValid
        }
        
        fun handleSignUp() {
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
                        delay(1500) // Show success message briefly
                        onSignUpSuccess()
                    } catch (e: Exception) {
                        // Show network error
                        println("Network error: ${e.message}")
                        // In production, consider using a proper logging framework
                        // or error tracking service like Crashlytics
                        showNetworkError = true
                    } finally {
                        isLoading = false
                    }
                }
            }
        }
        
        if (showExistingEmailError) {
            AlertDialog(
                onDismissRequest = { showExistingEmailError = false },
                title = { Text("Email Already Exists") },
                text = { Text("The email address $email is already registered. Please use a different email or try to log in.") },
                confirmButton = {
                    Button(onClick = { showExistingEmailError = false }) {
                        Text("OK")
                    }
                }
            )
        }
        
        if (showNetworkError) {
            AlertDialog(
                onDismissRequest = { showNetworkError = false },
                title = { Text("Connection Error") },
                text = { Text("We encountered a problem connecting to our servers. Please check your internet connection and try again.") },
                confirmButton = {
                    Button(onClick = { showNetworkError = false }) {
                        Text("Retry")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showNetworkError = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
        
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text("Account Created!") },
                text = { 
                    Column {
                        Text("Your PayStell account has been created successfully!")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Redirecting to your wallet...", color = Color.Gray)
                    }
                },
                confirmButton = { }
            )
        }
        
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Create Account") },
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
                    
                    Spacer(modifier = Modifier.height(28.dp))
                    
                    Text(
                        text = "Join PayStell",
                        style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Create an account to start making stellar payments",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Password strength indicator
                    if (password.isNotEmpty()) {
                        PasswordStrengthMeter(password)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    
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
                            if (passwordError.isNotEmpty() || confirmPasswordError.isNotEmpty()) validateInputs()
                        },
                        isError = passwordError.isNotEmpty(),
                        errorMessage = passwordError
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    PasswordInputField(
                        value = confirmPassword,
                        onValueChange = { 
                            confirmPassword = it
                            if (confirmPasswordError.isNotEmpty()) validateInputs()
                        },
                        label = "Confirm Password",
                        isError = confirmPasswordError.isNotEmpty(),
                        errorMessage = confirmPasswordError
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = Color(0xFFF5F5F5)
                    ) {
                        Text(
                            text = "Password requirements:\n" +
                                    "• At least 8 characters\n" +
                                    "• At least one uppercase letter (A-Z)\n" +
                                    "• At least one lowercase letter (a-z)\n" +
                                    "• At least one number (0-9)\n" +
                                    "• At least one special character (@$!%*?&)",
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    PayStellButton(
                        text = "Create Account",
                        onClick = { handleSignUp() },
                        enabled = !isLoading
                    )
                }
            }
        }
    }
}

@Composable
fun PasswordStrengthMeter(password: String) {
    val strength = calculatePasswordStrength(password)
    val color = when {
        strength < 0.3 -> ErrorRed
        strength < 0.7 -> Color(0xFFFFA000) // Amber
        else -> SuccessGreen
    }
    
    val strengthText = when {
        strength < 0.3 -> "Weak"
        strength < 0.7 -> "Moderate"
        else -> "Strong"
    }
    
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Password Strength: $strengthText",
            style = MaterialTheme.typography.caption,
            color = color
        )
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = strength.toFloat(),
            modifier = Modifier.fillMaxWidth(),
            color = color
        )
    }
}

private fun calculatePasswordStrength(password: String): Double {
    if (password.isEmpty()) return 0.0
    
    var score = 0.0
    
    // Length
    if (password.length >= 8) score += 0.2
    if (password.length >= 12) score += 0.1
    
    // Complexity
    if (password.any { it.isUpperCase() }) score += 0.2
    if (password.any { it.isLowerCase() }) score += 0.1
    if (password.any { it.isDigit() }) score += 0.2
    if (password.any { !it.isLetterOrDigit() }) score += 0.2
    
    return score
} 