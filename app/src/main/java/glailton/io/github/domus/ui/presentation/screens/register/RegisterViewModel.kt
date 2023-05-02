package glailton.io.github.domus.ui.presentation.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import glailton.io.github.domus.core.data.FirebaseResult
import glailton.io.github.domus.core.utils.authErrors
import glailton.io.github.domus.firebase.FirebaseAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(private val firebaseAuth: FirebaseAuthRepository) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun signup(
        name: String,
        email: String,
        phoneNumber: String,
        password: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {
            handleLoading()
            if (!validateRegisterForm(name, email, phoneNumber, password, confirmPassword)) {
                _state.update { it.copy(registerError = true) }
                hideLoading()
                return@launch
            }
            if (!validatePasswordConfirmation(password, confirmPassword)) {
                _state.update { it.copy(passwordMatchError = true, registerError = true) }
                hideLoading()
                return@launch
            }
            when (val result = firebaseAuth.signup(name, email, password)) {
                is FirebaseResult.Success -> {
                    hideLoading()
                    _state.update {
                        it.copy(
                            isSuccessRegister = true,
                            name = name,
                            email = email,
                            phoneNumber = phoneNumber
                        )
                    }
                }
                is FirebaseResult.Error -> {
                    hideLoading()
                    _state.update {
                        it.copy(
                            registerErrorMessage = authErrors[(result.exception as FirebaseAuthException).errorCode],
                            isLoading = false,
                            registerError = true
                        )
                    }
                }
            }
        }
    }

    fun hideErrorDialog() {
        _state.update {
            it.copy(registerError = false)
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

    private fun validateRegisterForm(
        name: String,
        email: String,
        phoneNumber: String,
        password: String,
        confirmPassword: String
    ) =
        name.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty()
                && password.isNotEmpty() && confirmPassword.isNotEmpty()

    private fun validatePasswordConfirmation(password: String, confirmPassword: String) =
        password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword
}