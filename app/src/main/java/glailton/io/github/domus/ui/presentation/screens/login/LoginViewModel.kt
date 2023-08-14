package glailton.io.github.domus.ui.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import glailton.io.github.domus.core.data.FirebaseResult
import glailton.io.github.domus.core.data.local.DataStoreManager
import glailton.io.github.domus.core.data.local.PreferenceDataStoreConstants.USER_KEY
import glailton.io.github.domus.core.utils.authErrors
import glailton.io.github.domus.firebase.FirebaseAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val firebaseAuth: FirebaseAuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun login() {
        viewModelScope.launch {
            handleLoading()
            val email = state.value.email
            val password = state.value.password
            if (!validateLoginForm(email, password)) {
                _state.update { it.copy(loginError = true) }
                hideLoading()
                return@launch
            }
            when (val result = firebaseAuth.login(email, password)) {
                is FirebaseResult.Success -> {
                    hideLoading()
                    dataStoreManager.putPreference(USER_KEY, result.data.uid)
                    _state.update {
                        it.copy(
                            isSuccessLogin = true,
                            email = email
                        )
                    }
                }
                is FirebaseResult.Error -> {
                    hideLoading()
                    _state.update {
                        it.copy(
                            loginError = true,
                            loginErrorMessage = authErrors[(result.exception as FirebaseAuthException).errorCode],
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun updateInfo(email: String = "", password: String = "") {
        when {
            email.isNotEmpty() -> _state.update { it.withEmail(email) }
            password.isNotEmpty() -> _state.update { it.withPassword(password) }
        }

    }

    fun hideErrorDialog() {
        _state.update {
            it.copy(loginError = false)
        }
    }

    private fun handleLoading() {
        _state.update {
            it.copy(isLoading = true)
        }
    }

    private fun hideLoading() {
        _state.update {
            it.copy(isLoading = false)
        }
    }

    private fun validateLoginForm(email: String, password: String) =
        email.isNotBlank() && password.isNotBlank()
}