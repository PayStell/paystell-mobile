package org.paystell.app.utils

/**
 * Validates an email address
 * @param email The email to validate
 * @return True if the email is valid, false otherwise
 */
fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
    return email.matches(emailRegex.toRegex())
}

/**
 * Validates a password based on strength requirements
 * @param password The password to validate
 * @return True if the password meets requirements, false otherwise
 */
fun isValidPassword(password: String): Boolean {
    // At least 8 characters, one uppercase, one lowercase, one number, one special character
    val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"
    return password.matches(passwordRegex.toRegex())
}

/**
 * Checks if passwords match
 * @param password The main password
 * @param confirmPassword The confirmation password
 * @return True if passwords match, false otherwise
 */
fun doPasswordsMatch(password: String, confirmPassword: String): Boolean {
    return password == confirmPassword
}

/**
 * Object containing validation error messages
 */
object ValidationErrorMessages {
    const val EMPTY_EMAIL = "Email cannot be empty"
    const val INVALID_EMAIL = "Please enter a valid email address"
    const val EMPTY_PASSWORD = "Password cannot be empty"
    const val INVALID_PASSWORD = "Password must be at least 8 characters and include uppercase, lowercase, number, and special character"
    const val PASSWORDS_DONT_MATCH = "Passwords do not match"
} 