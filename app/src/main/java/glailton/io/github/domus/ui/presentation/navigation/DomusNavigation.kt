package glailton.io.github.domus.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import glailton.io.github.domus.ui.presentation.screens.login.LoginScreen

@Composable
fun DomusNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LoginScreenRoute.route
    ) {
        navLoginScreen(navController)
    }
}

fun NavGraphBuilder.navLoginScreen(navController: NavHostController) {
    composable(route = Routes.LoginScreenRoute.route) {
        LoginScreen()
    }
}