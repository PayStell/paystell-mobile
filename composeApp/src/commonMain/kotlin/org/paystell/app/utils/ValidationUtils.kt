package org.paystell.app.utils

// Pre-compiled regex pattern for email validation
private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

/**
 * Validate if the given string is a valid email address
 * @param email String to validate
 * @return true if the string is a valid email, false otherwise
 */
fun isValidEmail(email: String): Boolean {
    return EMAIL_REGEX.matches(email)
}

/**
 * Validates a password based on strength requirements
 * @param password The password to validate
 * @return True if the password meets requirements, false otherwise
 */
fun isValidPassword(password: String): Boolean {
    // At least 8 characters, one uppercase, one lowercase, one number, one special character
    return password.length >= 8 && password.contains(Regex("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])"))
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
    const val EMPTY_EMAIL = "Email is required"
    const val INVALID_EMAIL = "Please enter a valid email address"
    const val EMPTY_PASSWORD = "Password is required"
    const val PASSWORD_TOO_SHORT = "Password must be at least 8 characters long"
    const val PASSWORDS_DO_NOT_MATCH = "Passwords do not match"
} 