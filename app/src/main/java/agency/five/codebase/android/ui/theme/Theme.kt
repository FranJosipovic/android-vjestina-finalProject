package agency.five.codebase.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Color(187, 134, 252, 255),
    secondary = Color(5, 0, 73, 255),
    secondaryVariant = Color(33, 33, 34, 255),
    onSecondary = Color.Black
)

private val LightColorPalette = lightColors(
    primary = Color(236, 34, 107, 255),
    primaryVariant = Color(0, 0, 0, 255),
    secondary = Purple700,
    secondaryVariant = Color.LightGray,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun TodoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        systemUiController.setStatusBarColor(
            color = DarkColorPalette.primary
        )
        DarkColorPalette
    } else {
        systemUiController.setStatusBarColor(
            color = LightColorPalette.primary
        )
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
