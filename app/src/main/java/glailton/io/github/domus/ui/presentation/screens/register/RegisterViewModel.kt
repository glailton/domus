package glailton.io.github.domus.ui.presentation.screens.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import glailton.io.github.domus.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun register(
        name: String,
        email: String,
        phoneNumber: String,
        password: String,
        confirmPassword: String
    ) {
        val errorMessage = if(name.isBlank() || email.isBlank() || phoneNumber.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            R.string.error_input_empty
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            R.string.error_not_a_valid_email
        } else if(!Patterns.PHONE.matcher(phoneNumber).matches()) {
            R.string.error_not_a_valid_phone_number
        } else if(password != confirmPassword) {
            R.string.error_incorrectly_repeated_password
        } else null

        errorMessage?.let {
            _state.update {
                it.copy(errorMessage = it.errorMessage)
            }
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    displayProgressBar = true
                )
            }
        }
    }

    fun hideErrorDialog() {
        _state.update {
            it.copy(errorMessage = null)
        }
    }
}