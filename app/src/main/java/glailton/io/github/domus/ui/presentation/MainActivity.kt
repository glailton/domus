package glailton.io.github.domus.ui.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import glailton.io.github.domus.ui.presentation.navigation.DomusNavigation
import glailton.io.github.domus.ui.theme.DomusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DomusTheme {
                DomusNavigation()
            }
        }
    }
}