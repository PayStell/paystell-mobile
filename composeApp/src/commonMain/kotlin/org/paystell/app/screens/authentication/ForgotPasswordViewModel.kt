package org.paystell.app.screens.authentication

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.paystell.app.utils.ValidationErrorMessages
import org.paystell.app.utils.isValidEmail
import kotlin.random.Random

/**
 * ViewModel to manage state for the Forgot Password flow
 */
class ForgotPasswordViewModel {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    
    // Form input states
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    
    private val _emailError = MutableStateFlow("")
    val emailError: StateFlow<String> = _emailError
    
    // UI states
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    private val _showNetworkError = MutableStateFlow(false)
    val showNetworkError: StateFlow<Boolean> = _showNetworkError
    
    private val _showUnknownEmailError = MutableStateFlow(false)
    val showUnknownEmailError: StateFlow<Boolean> = _showUnknownEmailError
    
    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog: StateFlow<Boolean> = _showSuccessDialog
    
    /**
     * Update email input and validate if needed
     */
    fun updateEmail(value: String) {
        _email.value = value
        if (_emailError.value.isNotEmpty()) {
            validateEmail()
        }
    }
    
    /**
     * Validate the email input
     */
    fun validateEmail(): Boolean {
        val trimmedEmail = _email.value.trim()
        if (trimmedEmail.isEmpty()) {
            _emailError.value = ValidationErrorMessages.EMPTY_EMAIL
            return false
        } else if (!isValidEmail(trimmedEmail)) {
            _emailError.value = ValidationErrorMessages.INVALID_EMAIL
            return false
        } else if (trimmedEmail == "unknown@example.com") {
            // Mock check for unknown email
            _showUnknownEmailError.value = true
            return false
        }

        _emailError.value = ""
        return true
    }
    
    /**
     * Submit the reset password request
     */
    fun submitResetRequest(onResetRequestSent: () -> Unit) {
        if (validateEmail()) {
            _isLoading.value = true
            
            // Simulate network request
            viewModelScope.launch {
                try {
                    // Simulate network delay
                    delay(1500)

                    // Simulate random network error (20% chance)
                    if (Random.nextDouble() < 0.2) {
                        throw Exception("Network error")
                    }

                    // Success path
                    _showSuccessDialog.value = true
                    delay(3000) // Show success message briefly
                    onResetRequestSent()
                } catch (e: Exception) {
                    // Log the error and show network error dialog
                    println("Password reset error: ${e.message}") // Replace with proper logging
                    _showNetworkError.value = true
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }
    
    /**
     * Reset dialog visibility states
     */
    fun dismissNetworkError() {
        _showNetworkError.value = false
    }
    
    fun dismissUnknownEmailError() {
        _showUnknownEmailError.value = false
    }
} 