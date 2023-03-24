package glailton.io.github.domus.ui.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Routes(
    val route: String,
    val arguments: List<NamedNavArgument>
) {
    object LoginScreenRoute : Routes("login-screen", emptyList())
    object RegistrationScreenRoute : Routes("registration-screen", emptyList())
    object HomeScreenRoute: Routes(
        "home-screen",
        listOf(
            navArgument("email"){ type = NavType.StringType },
            navArgument("password"){ type = NavType.StringType }
        )
    )
}
