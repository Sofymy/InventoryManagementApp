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
    primary = RaspberryRed,
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


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Immutable
data class BottomNavigationBarColorScheme(
    val topContainerBar: Color = Color.Unspecified,
    val containerTextColor : Color = Color.Unspecified,
    val selectedIconColor: Color = Color.Unspecified,
    val selectedTextColor: Color = Color.Unspecified,
    val unselectedIconColor: Color = Color.Unspecified,
    val unselectedTextColor: Color = Color.Unspecified,
    val bottomContainerBar: Color = Color.Unspecified
)

@SuppressLint("CompositionLocalNaming")
val NavigationBarCustomColorsPalette = staticCompositionLocalOf { BottomNavigationBarColorScheme() }


val LightBottomNavigationBarColorScheme = BottomNavigationBarColorScheme(
    topContainerBar = Grey,
    containerTextColor = Color.Black,
    selectedIconColor = RaspberryRed,
    selectedTextColor = RaspberryRed,
    unselectedIconColor = Color.Black,
    unselectedTextColor = Color.Black,
    bottomContainerBar = Color.White
)

val DarkBottomNavigationBarColorScheme = BottomNavigationBarColorScheme(
    topContainerBar = EerieBlack40Black,
    containerTextColor = Color.White,
    selectedIconColor = RaspberryRed30White,
    selectedTextColor = RaspberryRed30White,
    unselectedIconColor = Color.White,
    unselectedTextColor = Color.White,
    bottomContainerBar = EerieBlack
)
@Composable
fun IMAAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )

    val bottomNavigationBarColorScheme =
        if (darkTheme) DarkBottomNavigationBarColorScheme
        else LightBottomNavigationBarColorScheme

    CompositionLocalProvider(
        NavigationBarCustomColorsPalette provides bottomNavigationBarColorScheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}