package glailton.io.github.domus.ui.presentation.screens.register

import androidx.annotation.StringRes

data class RegisterState(
    val isSuccessRegister: Boolean = false,
    val registerError: Boolean = false,
    val passwordMatchError: Boolean = false,
    @StringRes val registerErrorMessage: Int? = null,
    val isLoading: Boolean = false,
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val confirmPassword: String = "",
) {
    fun withName(name: String) = copy(name = name)
    fun withEmail(email: String) = copy(email = email)
    fun withPhoneNumber(phoneNumber: String) = copy(phoneNumber = phoneNumber)
    fun withPassword(password: String) = copy(password = password)
    fun withConfirmPassword(confirmPassword: String) = copy(confirmPassword = confirmPassword)
}