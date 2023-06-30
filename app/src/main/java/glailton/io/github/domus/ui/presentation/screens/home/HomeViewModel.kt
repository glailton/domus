package glailton.io.github.domus.ui.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import glailton.io.github.domus.core.data.FirebaseResult
import glailton.io.github.domus.core.utils.authErrors
import glailton.io.github.domus.firebase.FirebaseAuthRepository
import glailton.io.github.domus.firebase.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val firebaseAuth: FirebaseAuthRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    val user = firebaseAuth.currentUser

    fun getUser() {
        viewModelScope.launch {
            handleLoading()
            val userId = user?.uid

            userId.let {
                when (val result = firestoreRepository.getUser(it!!)) {
                    is FirebaseResult.Success -> {
                        hideLoading()
                        _state.update { res ->
                            res.copy(
                                user = result.data,
                                isLoading = false
                            )
                        }
                    }
                    is FirebaseResult.Error -> {
                        hideLoading()
                        _state.update { res ->
                            res.copy(
                                errorMessage = authErrors[(result.exception as FirebaseAuthException).errorCode],
                                isLoading = false
                            )
                        }
                    }
                }
            }

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
}