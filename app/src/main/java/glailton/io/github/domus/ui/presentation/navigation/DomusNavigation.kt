package glailton.io.github.domus.ui.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import glailton.io.github.domus.ui.presentation.screens.home.HomeScreen
import glailton.io.github.domus.ui.presentation.screens.login.LoginScreen
import glailton.io.github.domus.ui.presentation.screens.login.LoginViewModel
import glailton.io.github.domus.ui.presentation.screens.register.RegisterScreen
import glailton.io.github.domus.ui.presentation.screens.register.RegisterViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DomusNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LoginScreenRoute.route
    ) {
        navLoginScreen(navController)
        navRegistrationScreen(navController)
        navHomeScreen()
    }
}

fun NavGraphBuilder.navLoginScreen(navController: NavHostController) {
    composable(
        route = Routes.LoginScreenRoute.route
    ) {
        val viewModel: LoginViewModel = koinViewModel()
        val state = viewModel.state.collectAsState().value
        val email = state.email
        val password = state.password

        if (state.isSuccessLogin) {
            navController.navigate(
                Routes.HomeScreenRoute.route
            ) {
                popUpTo(Routes.LoginScreenRoute.route) {
                    inclusive = true
                }
            }
        } else {
            LoginScreen(
                viewModel = viewModel,
                onLogin = viewModel::signInAuthUser,
                onNavigateToRegister = {
                    navController.navigate(Routes.RegistrationScreenRoute.route)
                },
                onDismissDialog = viewModel::hideErrorDialog
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navRegistrationScreen(navController: NavHostController) {
    composable(
        route = Routes.RegistrationScreenRoute.route
    ) {
        val viewModel: RegisterViewModel = koinViewModel()
        RegisterScreen(
            viewModel = viewModel,
            onRegister = viewModel::register,
            onBack = {
                navController.popBackStack()
            },
            onDismissDialog = viewModel::hideErrorDialog
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navHomeScreen() {
    composable(
        route = Routes.HomeScreenRoute.route
    ){
        HomeScreen()
    }
}