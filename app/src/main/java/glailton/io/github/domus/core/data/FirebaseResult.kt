package glailton.io.github.domus.core.data

sealed class FirebaseResult<out R> {
    data class Success<out T>(val data: T) : FirebaseResult<T>()
    data class Error(val exception: Throwable) : FirebaseResult<Nothing>()
}