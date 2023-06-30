package glailton.io.github.domus.ui.presentation.navigation

import androidx.navigation.NamedNavArgument

sealed class Routes(
    val route: String,
    val arguments: List<NamedNavArgument>
) {
    object LoginScreenRoute : Routes("login-screen", emptyList())
    object RegistrationScreenRoute : Routes("registration-screen", emptyList())
    object HomeScreenRoute: Routes("home-screen", emptyList())
    object ProfileScreenRoute: Routes("profile-screen", emptyList())
}
