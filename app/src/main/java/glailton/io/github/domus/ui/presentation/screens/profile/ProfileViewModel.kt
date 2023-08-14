package glailton.io.github.domus.ui.presentation.screens.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import glailton.io.github.domus.core.data.FirebaseResult
import glailton.io.github.domus.core.models.User
import glailton.io.github.domus.core.utils.authErrors
import glailton.io.github.domus.firebase.FirebaseAuthRepository
import glailton.io.github.domus.firebase.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val firebaseAuth: FirebaseAuthRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
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
                        _state.update { profile ->
                            profile.withUserProfile(result.data)
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

    fun updateInfo(
        name: String = "",
        email: String = "",
        photoUrl: String = "",
        phoneNumber: String = "",
        birthday: String = ""
    ) {
        when {
            name.isNotEmpty() -> {
                _state.update { it.withName(name) }
            }

            email.isNotEmpty() -> {
                _state.update { it.withEmail(email) }
            }

            photoUrl.isNotEmpty() -> {
                _state.update { it.withPhotoUrl(photoUrl) }
            }

            phoneNumber.isNotEmpty() -> {
                _state.update { it.withPhoneNumber(phoneNumber) }
            }

            birthday.isNotEmpty() -> {
                _state.update { it.withBirthday(birthday) }
            }
        }
    }

    fun saveImage(uri: Uri) {
        viewModelScope.launch {
            handleLoading()
            val userId = user?.uid

            userId.let {
                when (val result = firestoreRepository.saveImage(uri, it!!)) {
                    is FirebaseResult.Success -> {
                        hideLoading()
                        _state.update { profile ->
                            profile.copy(photoUrl = result.data)
                        }
                    }

                    is FirebaseResult.Error -> {
                        hideLoading()
                        _state.update { res ->
                            res.copy(
                                errorMessage = authErrors[(result.exception as FirebaseAuthException).errorCode],
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateUser() {
        viewModelScope.launch {
            val state = _state.value
            val userId = user?.uid

            userId?.let {
                val request = User(state.name, state.email, state.birthday, state.photoUrl, state.phoneNumber, userId)

                when (val result = firestoreRepository.updateUser(request)) {
                    is FirebaseResult.Success -> {
                        hideLoading()
                        _state.update { it.copy(isSaved = true) }
                    }

                    is FirebaseResult.Error -> {
                        hideLoading()
                        _state.update { res ->
                            res.copy(
                                isSaved = false,
                                errorMessage = authErrors[(result.exception as FirebaseAuthException).errorCode],
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