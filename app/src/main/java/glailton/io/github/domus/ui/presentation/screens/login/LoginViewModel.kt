package glailton.io.github.domus.ui.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import glailton.io.github.domus.firebase.FirebaseAuthRepository
import glailton.io.github.domus.firebase.FirebaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val firebaseAuth: FirebaseAuthRepository
): ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun signInAuthUser(email: String, password: String) {
        viewModelScope.launch {
            if (!validateLoginForm(email, password)) {
                _state.update { it.copy(loginError = "email and password can not be empty") }
                return@launch
            }
            firebaseAuth.signInAuthUser(email, password).collect { result ->
                when(result) {
                    is FirebaseResult.Success -> {
                        _state.update { it.copy(isSuccessLogin = true) }
                    }
                    is FirebaseResult.Error -> {
                        _state.update { it.copy(loginError = result.exception.message) }
                    }
                    is FirebaseResult.Loading -> {
                        _state.update { it.copy(displayProgressBar = true) }
                    }
                }
            }
        }
    }

    fun hideErrorDialog() {
        _state.update {
            it.copy(loginError = null)
        }
    }

    private fun validateLoginForm(email: String, password: String) =
        email.isNotBlank() && password.isNotBlank()
}