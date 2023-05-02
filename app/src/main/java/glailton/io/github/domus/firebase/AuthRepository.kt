package glailton.io.github.domus.firebase

import com.google.firebase.auth.FirebaseUser
import glailton.io.github.domus.core.data.FirebaseResult

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): FirebaseResult<FirebaseUser>
    suspend fun signup(name: String, email: String, password: String): FirebaseResult<FirebaseUser>
    fun logout()
}