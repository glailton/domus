package glailton.io.github.domus.firebase

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import glailton.io.github.domus.core.data.FirebaseResult
import glailton.io.github.domus.core.models.User

interface FirestoreRepository {
    val currentUser: FirebaseUser?
    suspend fun saveUser(user: User)
    suspend fun updateUser(user: User): FirebaseResult<Boolean>
    suspend fun getUser(userId: String): FirebaseResult<User>
    suspend fun saveImage(uri: Uri, userId: String): FirebaseResult<String>
}