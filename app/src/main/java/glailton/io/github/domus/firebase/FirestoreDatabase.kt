package glailton.io.github.domus.firebase

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class FirestoreDatabase {
    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    fun hasUser() = currentUser != null

    fun getUserId() = currentUser?.uid.orEmpty()

    suspend fun createUser(
        email:String,
        password:String,
    ): Flow<FirebaseResult<String>> = callbackFlow {
        Firebase.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { trySend(FirebaseResult.Success("User Added")) }
            .addOnFailureListener { trySend(FirebaseResult.Error(Throwable(it.message))) }
        awaitClose { close() }
    }
}