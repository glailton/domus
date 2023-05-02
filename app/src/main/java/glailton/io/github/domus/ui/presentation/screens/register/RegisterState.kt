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
)