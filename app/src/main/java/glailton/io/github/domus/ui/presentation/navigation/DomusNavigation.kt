package glailton.io.github.domus.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import glailton.io.github.domus.ui.presentation.screens.home.HomeScreen
import glailton.io.github.domus.ui.presentation.screens.home.HomeViewModel
import glailton.io.github.domus.ui.presentation.screens.login.LoginScreen
import glailton.io.github.domus.ui.presentation.screens.login.LoginViewModel
import glailton.io.github.domus.ui.presentation.screens.profile.ProfileScreen
import glailton.io.github.domus.ui.presentation.screens.profile.ProfileViewModel
import glailton.io.github.domus.ui.presentation.screens.register.RegisterScreen
import glailton.io.github.domus.ui.presentation.screens.register.RegisterViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DomusNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navLoginScreen(navController)
        navRegistrationScreen(navController)
        navHomeScreen(navController)
        navProfileScreen(navController)
    }
}

fun NavGraphBuilder.navLoginScreen(navController: NavHostController) {
    composable(
        route = Routes.LoginScreenRoute.route
    ) {
        val viewModel: LoginViewModel = koinViewModel()
        val state = viewModel.state.collectAsState().value

        LaunchedEffect(state.isSuccessLogin) {
            if (state.isSuccessLogin) {
                navController.navigate(
                    Routes.HomeScreenRoute.route
                ) {
                    popUpTo(Routes.LoginScreenRoute.route) {
                        inclusive = true
                    }
                }
            }
        }

        LoginScreen(
            viewModel = viewModel,
            onLogin = viewModel::login,
            onNavigateToRegister = {
                navController.navigate(Routes.RegistrationScreenRoute.route)
            },
            onDismissDialog = viewModel::hideErrorDialog
        )
    }
}

fun NavGraphBuilder.navRegistrationScreen(navController: NavHostController) {
    composable(
        route = Routes.RegistrationScreenRoute.route
    ) {
        val viewModel: RegisterViewModel = koinViewModel()
        val state = viewModel.state.collectAsState().value

        LaunchedEffect(state.isSuccessRegister) {
            if (state.isSuccessRegister) {
                navController.navigate(
                    Routes.HomeScreenRoute.route
                ) {
                    popUpTo(Routes.LoginScreenRoute.route) {
                        inclusive = true
                    }
                }
            }
        }

        RegisterScreen(
            viewModel = viewModel,
            onRegister = viewModel::signup,
            onBack = {
                navController.popBackStack()
            },
            onDismissDialog = viewModel::hideErrorDialog
        )
    }
}

fun NavGraphBuilder.navHomeScreen(navController: NavHostController) {
    composable(
        route = Routes.HomeScreenRoute.route
    ) {
        val viewModel: HomeViewModel = koinViewModel()

        HomeScreen(
            viewModel = viewModel,
            onNavigateToProfile = {
                navController.navigate(Routes.ProfileScreenRoute.route)
            }
        )
    }
}

fun NavGraphBuilder.navProfileScreen(navController: NavHostController) {
    composable(
        route = Routes.ProfileScreenRoute.route
    ) {
        val viewModel: ProfileViewModel = koinViewModel()

        ProfileScreen(
            viewModel = viewModel
        )
    }
}