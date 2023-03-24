package glailton.io.github.domus.ui.presentation.screens.register

import androidx.annotation.StringRes

data class RegisterState(
    val isSuccessRegister: Boolean = false,
    val displayProgressBar: Boolean = false,
    @StringRes val errorMessage: Int? = null
)