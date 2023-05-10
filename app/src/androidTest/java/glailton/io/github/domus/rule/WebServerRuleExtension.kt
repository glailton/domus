package glailton.io.github.domus.rule

import com.google.firebase.auth.FirebaseAuthException
import glailton.io.github.domus.core.data.FirebaseResult

fun WebServerRule.mockLoginError() {
    enqueueResponse(
        FirebaseResult.Error(FirebaseAuthException("ERROR_WRONG_PASSWORD", "The password is invalid or the user does not have a password."))
    )
}