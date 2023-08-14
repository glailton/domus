package glailton.io.github.domus.ui.presentation.screens.profile

import androidx.annotation.StringRes
import glailton.io.github.domus.core.models.User

data class ProfileState(
    val name: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val phoneNumber: String = "",
    val birthday: String = "",
    val user: User? = null,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    @StringRes val errorMessage: Int? = null
) {
    fun withUserProfile(user: User) = copy(
        name = user.name,
        email = user.email,
        photoUrl = user.photo,
        phoneNumber = user.phoneNumber,
        birthday = user.birthday
    )
    fun withName(name: String) = copy(name = name)
    fun withEmail(email: String) = copy(email = email)
    fun withPhotoUrl(photoUrl: String) = copy(photoUrl = photoUrl)
    fun withPhoneNumber(phoneNumber: String) = copy(phoneNumber = phoneNumber)
    fun withBirthday(birthday: String) = copy(birthday = birthday)
}