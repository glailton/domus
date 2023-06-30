package glailton.io.github.domus.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import glailton.io.github.domus.core.data.FirebaseResult
import glailton.io.github.domus.core.data.await
import glailton.io.github.domus.core.models.User

class FirebaseAuthRepositoryImpl(
    private val firestoreRepository: FirestoreRepository
): FirebaseAuthRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(
        email: String,
        password: String
    ): FirebaseResult<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            FirebaseResult.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            FirebaseResult.Error(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun signup(name: String, email: String, password: String): FirebaseResult<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            firestoreRepository.saveUser(
                User(name, email, "", "", "", result.user?.uid ?: "")
            )
            FirebaseResult.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            FirebaseResult.Error(e)
        }
    }


}