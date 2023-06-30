package glailton.io.github.domus.ui.presentation.screens.home

import androidx.annotation.StringRes
import glailton.io.github.domus.core.models.User

data class HomeState(
    val user: User? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null,
)