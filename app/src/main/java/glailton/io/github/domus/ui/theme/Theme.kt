package glailton.io.github.domus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

//private val DarkColorPalette = darkColors(
//    primary =   BLUE900,
//    primaryVariant = BLUE950,
//    secondary = CYAN900,
//    secondaryVariant = CYAN800,
//    background = BLUEGREY900,
//    surface = BLUEGREY800,
//    error = RED800,
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onBackground = Color.White,
//    onSurface = Color.White,
//    onError = Color.White
//)
//
//private val LightColorPalette = lightColors(
//    primary =   Blue500,
//    primaryVariant = BLUE800,
//    secondary = CYAN500,
//    secondaryVariant = CYAN700,
//    background = LIGHTBLUE50,
//    surface = Color.White,
//    error = RED600,
//    onPrimary = Color.Black,
//    onSecondary = Color.Black,
//    onBackground = Color.Black,
//    onSurface = Color.Black,
//    onError = Color.Black
//)

private val LightColors = lightColors(
    primary = light_primary,
    primaryVariant = light_primaryVariant,
    onPrimary = light_onPrimary,
    secondary = light_secondary,
    onSecondary = light_onSecondary,
    secondaryVariant = light_secondaryVariant,
    error = light_error,
    onError = light_onError,
    background = light_background,
    onBackground = light_onBackground,
    surface = light_surface,
    onSurface = light_onSurface
)


private val DarkColors = darkColors(
    primary = dark_primary,
    primaryVariant = dark_primaryVariant,
    onPrimary = dark_onPrimary,
    secondary = dark_secondary,
    secondaryVariant = dark_secondaryVariant,
    onSecondary = dark_onSecondary,
    error = dark_error,
    onError = dark_onError,
    background = dark_background,
    onBackground = dark_onBackground,
    surface = dark_surface,
    onSurface = dark_onSurface
)

@Composable
fun DomusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}