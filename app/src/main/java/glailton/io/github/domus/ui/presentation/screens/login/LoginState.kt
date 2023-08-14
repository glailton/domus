package glailton.io.github.domus.ui.presentation.screens.login

import androidx.annotation.StringRes

data class LoginState(
    val email: String = "",
    val password: String = "",
    val loginError: Boolean = false,
    @StringRes val loginErrorMessage: Int? = null,
    val isSuccessLogin: Boolean = false,
    val isLoading: Boolean = false
) {
    fun withEmail(email: String) = copy(email = email)
    fun withPassword(password: String) = copy(password = password)
}