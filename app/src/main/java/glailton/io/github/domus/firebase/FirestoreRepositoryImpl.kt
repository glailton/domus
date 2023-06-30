package glailton.io.github.domus.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import glailton.io.github.domus.core.data.FirebaseResult
import glailton.io.github.domus.core.data.await
import glailton.io.github.domus.core.data.suspendableAwait
import glailton.io.github.domus.core.models.User


class FirestoreRepositoryImpl : FirestoreRepository {
    private val userRef: CollectionReference = Firebase.firestore.collection(USER_COLLECTION_REF)
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun saveUser(user: User) {
        userRef.document().set(user).await()
    }

    override suspend fun getUser(userId: String): FirebaseResult<User> {
        return try {
            val result = userRef.whereEqualTo("userId", userId).get().suspendableAwait()
            FirebaseResult.Success(result.documents.first().toObject(User::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            FirebaseResult.Error(e)
        }
    }

    fun hasUser() = currentUser != null

    fun getUserId() = currentUser?.uid.orEmpty()

    companion object {
        const val USER_COLLECTION_REF = "users"
    }
}