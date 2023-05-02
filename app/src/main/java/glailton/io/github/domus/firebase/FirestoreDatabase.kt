package glailton.io.github.domus.firebase

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class FirestoreDatabase {
    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    fun hasUser() = currentUser != null

    fun getUserId() = currentUser?.uid.orEmpty()
}