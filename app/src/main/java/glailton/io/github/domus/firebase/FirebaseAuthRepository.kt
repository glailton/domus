package glailton.io.github.domus.firebase

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseAuthRepository {
    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    fun signInAuthUser(
        email: String,
        password: String
    ): Flow<FirebaseResult<String>> = callbackFlow {
        trySend(FirebaseResult.Loading)
        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                trySend(FirebaseResult.Success("User Connected"))
            } else {
                trySend(
                    FirebaseResult.Error(
                        Throwable(
                            task.exception?.message ?: "Some Error Occurred"
                        )
                    )
                )
            }
        }
        awaitClose { close() }
    }

    suspend fun signOutAuthUser(): Flow<FirebaseResult<String>> = callbackFlow {
        trySend(FirebaseResult.Loading)
        try {
            Firebase.auth.signOut()
            trySend(FirebaseResult.Success("User Disconnected"))
        } catch (e: Exception) {
            trySend(FirebaseResult.Error(Throwable(e.message)))
        }
        awaitClose { close() }
    }

    suspend fun signUpAuthUser(
        email: String,
        password: String
    ): Flow<FirebaseResult<String>> = callbackFlow {
        trySend(FirebaseResult.Loading)
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(FirebaseResult.Success("User Signed"))
                } else {
                    trySend(
                        FirebaseResult.Error(
                            Throwable(
                                task.exception?.message ?: "Some Error Occurred"
                            )
                        )
                    )
                }
            }
        awaitClose { close() }
    }
}