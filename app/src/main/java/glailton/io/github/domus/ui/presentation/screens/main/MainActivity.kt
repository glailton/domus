package glailton.io.github.domus.ui.presentation.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.BoxWithConstraints
import glailton.io.github.domus.ui.presentation.navigation.DomusNavigation
import glailton.io.github.domus.ui.presentation.navigation.Routes
import glailton.io.github.domus.ui.theme.DomusTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    var userId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel = getViewModel()

        viewModel.getUserId()

        setContent {
            DomusTheme {
                BoxWithConstraints {
                    DomusNavigation(startDestination = if
                            (viewModel.userId.isNotEmpty()) Routes.HomeScreenRoute.route
                    else Routes.LoginScreenRoute.route)
                }
            }
        }
    }
}