package glailton.io.github.domus.ui.presentation.screens.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val loginError: String? = null,
    val isSuccessLogin: Boolean = false,
    val displayProgressBar: Boolean = false
)