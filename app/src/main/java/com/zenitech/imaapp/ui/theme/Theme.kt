package com.zenitech.imaapp.ui.theme

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = RaspberryRed30White,
    secondary = EerieBlack,
    background = EerieBlack40Black,
    surface = EerieBlack40Black,
    onSurface = White,
    onBackground = White
)

private val LightColorScheme = lightColorScheme(
    primary = RaspberryRed,
    secondary = EerieBlack,
    background = Grey,
    surface = Grey,
    onSurface = Color.Black,
    onBackground = Color.Black
)

@Immutable
data class BottomNavigationBarColorScheme(
    val topContainerBarColor: Color = Color.Unspecified,
    val containerTextColor : Color = Color.Unspecified,
    val selectedIconColor: Color = Color.Unspecified,
    val selectedTextColor: Color = Color.Unspecified,
    val unselectedIconColor: Color = Color.Unspecified,
    val unselectedTextColor: Color = Color.Unspecified,
    val bottomContainerBarColor: Color = Color.Unspecified,
    val bottomContainerBarDividerColor: Color = Color.Unspecified
)

@Immutable
data class CardColorScheme(
    val containerColor: Color = Color.Unspecified,
    val borderColor: Color = Color.Unspecified,
    val arrowColor: Color = Color.Unspecified,
    val contentColor: Color = Color.Unspecified,
    val secondaryContentColor: Color = Color.Unspecified
)

@Immutable
data class ButtonColorScheme(
    val containerColor: Color = Color.Unspecified,
    val borderColor: Color = Color.Unspecified,
)

val LocalNavigationBarColorsPalette = staticCompositionLocalOf { BottomNavigationBarColorScheme() }

val LocalCardColorsPalette = staticCompositionLocalOf { CardColorScheme() }

val LocalButtonColorsPalette = staticCompositionLocalOf { ButtonColorScheme() }

val LightNavigationBarColorScheme = BottomNavigationBarColorScheme(
    topContainerBarColor = Grey,
    containerTextColor = Color.Black,
    selectedIconColor = RaspberryRed,
    selectedTextColor = RaspberryRed,
    unselectedIconColor = Color.Black,
    unselectedTextColor = Color.Black,
    bottomContainerBarColor = Color.White,
    bottomContainerBarDividerColor = Color.Black.copy(0.1f)
)

val DarkNavigationBarColorScheme = BottomNavigationBarColorScheme(
    topContainerBarColor = EerieBlack40Black,
    containerTextColor = Color.White,
    selectedIconColor = RaspberryRed30White,
    selectedTextColor = RaspberryRed30White,
    unselectedIconColor = Color.White,
    unselectedTextColor = Color.White,
    bottomContainerBarColor = EerieBlack,
    bottomContainerBarDividerColor = EerieBlack40White
)

val DarkCardColorScheme = CardColorScheme(
    containerColor = EerieBlack,
    arrowColor = EerieBlack40White,
    borderColor = EerieBlack40White,
    contentColor = Color.White,
    secondaryContentColor = Color.White.copy(0.3f),
)

val LightCardColorScheme = CardColorScheme(
    containerColor = Color.White,
    arrowColor = Color.Black.copy(0.1f),
    borderColor = Color.Black.copy(0.1f),
    contentColor = Color.Black,
    secondaryContentColor = Color.Black.copy(0.2f),
)

val DarkButtonColorScheme = ButtonColorScheme(
    containerColor = EerieBlack40BlackTransparent,
    borderColor = EerieBlack40White,
)

val LightButtonColorScheme = ButtonColorScheme(
    containerColor = WhiteTransparent,
    borderColor = Color.Black.copy(0.1f),
)


@Composable
fun IMAAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val systemUiController = rememberSystemUiController()
    if(darkTheme){
        systemUiController.setNavigationBarColor(
            color = EerieBlack
        )
        systemUiController.setStatusBarColor(
            color = EerieBlack40Black
        )
    }else{
        systemUiController.setNavigationBarColor(
            color = Color.White
        )
        systemUiController.setStatusBarColor(
            color = Grey
        )
    }

    val cardColorScheme =
        if (darkTheme) DarkCardColorScheme
        else LightCardColorScheme


    val bottomNavigationBarColorScheme =
        if (darkTheme) DarkNavigationBarColorScheme
        else LightNavigationBarColorScheme

    val buttonColorScheme =
        if (darkTheme) DarkButtonColorScheme
        else LightButtonColorScheme


    CompositionLocalProvider(
        LocalCardColorsPalette provides cardColorScheme,
        LocalButtonColorsPalette provides buttonColorScheme,
        LocalNavigationBarColorsPalette provides bottomNavigationBarColorScheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }

}
