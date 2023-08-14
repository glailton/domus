package glailton.io.github.domus.firebase

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import glailton.io.github.domus.core.data.FirebaseResult
import glailton.io.github.domus.core.data.await
import glailton.io.github.domus.core.data.suspendableAwait
import glailton.io.github.domus.core.models.User


class FirestoreRepositoryImpl : FirestoreRepository {
    private val userRef: CollectionReference = Firebase.firestore.collection(USER_COLLECTION_REF)
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val imagesStorageRef: FirebaseStorage = Firebase.storage
    private val imagesCollRef: CollectionReference = Firebase.firestore.collection(IMAGES_COLLECTION_REF)
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun saveUser(user: User) {
        userRef.document(user.userId).set(user).await()
    }

    override suspend fun updateUser(user: User): FirebaseResult<Boolean> {
        return try {
            val userData = hashMapOf<String,Any>(
                "name" to user.name,
                "email" to user.email,
                "birthday" to user.birthday,
                "photo" to user.photo,
                "phoneNumber" to user.phoneNumber,
                "userId" to user.userId,
            )
            userRef.document(user.userId).update(userData).await()
            FirebaseResult.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            FirebaseResult.Error(e)
        }
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

    override suspend fun saveImage(uri: Uri, userId: String): FirebaseResult<String> {
        val imageRef = imagesStorageRef.reference.child(IMAGES_COLLECTION_REF).child(userId)
        return try {
            val result = imageRef.putFile(uri).suspendableAwait().storage.downloadUrl.suspendableAwait()
            FirebaseResult.Success(result.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            FirebaseResult.Error(e)
        }
    }

    fun hasUser() = currentUser != null

    fun getUserId() = currentUser?.uid.orEmpty()

    companion object {
        const val USER_COLLECTION_REF = "users"
        const val IMAGES_COLLECTION_REF = "images"
    }
}