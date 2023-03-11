package glailton.io.github.domus.ui.presentation.navigation

sealed class Routes(
    val route: String
) {
    object LoginScreenRoute : Routes("login-screen")
    object RegistrationScreenRoute : Routes("registration-screen")
}
