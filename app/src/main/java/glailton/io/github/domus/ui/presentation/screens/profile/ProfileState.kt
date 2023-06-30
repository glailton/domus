package glailton.io.github.domus.ui.presentation.screens.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ProfileState(
    val name: MutableState<String> = mutableStateOf(""),
    val email: MutableState<String> = mutableStateOf(""),
    val photoUrl: MutableState<String> = mutableStateOf(""),
    val phoneNumber: MutableState<String> = mutableStateOf(""),
    val birthday: MutableState<String> = mutableStateOf(""),
    val isLoading: Boolean = false
)