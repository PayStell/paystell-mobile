package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import utils.Validators

class AuthViewModel {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    
    var emailError by mutableStateOf("")
    var passwordError by mutableStateOf("")
    var confirmPasswordError by mutableStateOf("")
    
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var isSuccess by mutableStateOf(false)
    
    fun validateEmail(): Boolean {
        return if (email.isEmpty()) {
            emailError = "Email cannot be empty"
            false
        } else if (!Validators.isValidEmail(email)) {
            emailError = "Please enter a valid email address"
            false
        } else {
            emailError = ""
            true
        }
    }
    
    fun validatePassword(): Boolean {
        return if (password.isEmpty()) {
            passwordError = "Password cannot be empty"
            false
        } else if (!Validators.isValidPassword(password)) {
            passwordError = "Password must be at least 8 characters with 1 uppercase, 1 lowercase, and 1 number"
            false
        } else {
            passwordError = ""
            true
        }
    }
    
    fun validateConfirmPassword(): Boolean {
        return if (confirmPassword.isEmpty()) {
            confirmPasswordError = "Please confirm your password"
            false
        } else if (!Validators.doPasswordsMatch(password, confirmPassword)) {
            confirmPasswordError = "Passwords do not match"
            false
        } else {
            confirmPasswordError = ""
            true
        }
    }
    
    fun validateLoginForm(): Boolean {
        return validateEmail() && validatePassword()
    }
    
    fun validateSignUpForm(): Boolean {
        return validateEmail() && validatePassword() && validateConfirmPassword()
    }
    
    suspend fun login() {
        if (!validateLoginForm()) return
        
        isLoading = true
        errorMessage = ""
        isSuccess = false
        
        try {
            // Simulate API call
            delay(1500)
            
            // For demo purposes, we'll just simulate a successful login
            // In a real app, you would call your authentication service here
            isSuccess = true
        } catch (e: Exception) {
            errorMessage = e.message ?: "An unknown error occurred"
        } finally {
            isLoading = false
        }
    }
    
    suspend fun signUp() {
        if (!validateSignUpForm()) return
        
        isLoading = true
        errorMessage = ""
        isSuccess = false
        
        try {
            // Simulate API call
            delay(1500)
            
            // For demo purposes, we'll just simulate a successful sign up
            // In a real app, you would call your authentication service here
            isSuccess = true
        } catch (e: Exception) {
            errorMessage = e.message ?: "An unknown error occurred"
        } finally {
            isLoading = false
        }
    }
    
    suspend fun resetPassword() {
        if (!validateEmail()) return
        
        isLoading = true
        errorMessage = ""
        isSuccess = false
        
        try {
            // Simulate API call
            delay(1500)
            
            // For demo purposes, we'll just simulate a successful password reset
            // In a real app, you would call your authentication service here
            isSuccess = true
        } catch (e: Exception) {
            errorMessage = e.message ?: "An unknown error occurred"
        } finally {
            isLoading = false
        }
    }
    
    fun resetState() {
        email = ""
        password = ""
        confirmPassword = ""
        emailError = ""
        passwordError = ""
        confirmPasswordError = ""
        isLoading = false
        errorMessage = ""
        isSuccess = false
    }
}

