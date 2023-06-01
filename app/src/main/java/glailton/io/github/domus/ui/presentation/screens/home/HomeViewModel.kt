package glailton.io.github.domus.ui.presentation.screens.home

import androidx.lifecycle.ViewModel
import glailton.io.github.domus.firebase.FirebaseAuthRepository

class HomeViewModel(
    private val firebaseAuth: FirebaseAuthRepository
) : ViewModel() {

    val user = firebaseAuth.currentUser
}