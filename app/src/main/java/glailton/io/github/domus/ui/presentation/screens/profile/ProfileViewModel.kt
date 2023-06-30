package glailton.io.github.domus.ui.presentation.screens.profile

import androidx.lifecycle.ViewModel
import glailton.io.github.domus.firebase.FirebaseAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(
    private val firebaseAuth: FirebaseAuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    val user = firebaseAuth.currentUser
}