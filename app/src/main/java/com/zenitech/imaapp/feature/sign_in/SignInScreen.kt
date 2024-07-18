package com.zenitech.imaapp.feature.sign_in

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.PrimaryButton
import kotlinx.coroutines.launch


@Composable
fun SignInScreen(
    onNavigateToMyDevices: () -> Unit
) {
    AnimatedContent()
    SignInContent(onNavigateToMyDevices)
}

@Composable
fun ScrollContent(){
    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState.isScrollInProgress) {
        scrollState.animateScrollTo(scrollState.value + 1, spring(stiffness = Spring.StiffnessHigh))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(scrollState)
            .paint(
                painter = painterResource(id = R.drawable.cover),
                alignment = Alignment.TopStart,
                contentScale = ContentScale.FillHeight,
            )
    )
}


@Composable
fun AnimatedContent(){
    val rotationTerminal = remember { Animatable(-30f) }
    val rotationLoupe = remember { Animatable(-10f) }
    val rotationText = remember { Animatable(-20f) }

    LaunchedEffect(Unit) {
        launch {
            rotationTerminal.animateTo(
                targetValue = 30f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 10000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
        launch {
            rotationLoupe.animateTo(
                targetValue = 10f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 4000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
        launch {
            rotationText.animateTo(
                targetValue = 20f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 10000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
    }

    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState.isScrollInProgress) {
        scrollState.animateScrollTo(scrollState.value + 1, spring(stiffness = Spring.StiffnessHigh))
    }

    ThemedContentWrapper(
        rotationTerminal = rotationTerminal.value,
        rotationLoupe = rotationLoupe.value,
        rotationText = rotationText.value,
        scrollState = scrollState
    )
}

@Composable
fun ThemedContent(
    scrollState: ScrollState,
    backgroundPainter: Painter,
    terminalIconPainter: Painter,
    findPersonIconPainter: Painter,
    textIconPainter: Painter,
    rotationTerminal: Float,
    rotationLoupe: Float,
    rotationText: Float
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(scrollState)
            .paint(
                painter = backgroundPainter,
                alignment = Alignment.TopStart,
                contentScale = ContentScale.FillHeight,
            )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = terminalIconPainter,
            contentDescription = null,
            modifier = Modifier
                .padding(20.dp, end = 60.dp)
                .align(Alignment.TopEnd)
                .size(200.dp)
                .graphicsLayer {
                    rotationZ = rotationTerminal
                }
        )
        Image(
            painter = findPersonIconPainter,
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 160.dp)
                .align(Alignment.CenterStart)
                .size(150.dp)
                .graphicsLayer {
                    rotationZ = rotationLoupe
                }
        )
        Image(
            painter = textIconPainter,
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 240.dp, end = 30.dp)
                .align(Alignment.BottomEnd)
                .size(180.dp)
                .graphicsLayer {
                    rotationZ = rotationText
                }
        )
    }
}

@Composable
fun ThemedContentWrapper(
    scrollState: ScrollState,
    rotationTerminal: Float,
    rotationLoupe: Float,
    rotationText: Float
) {
    if (isSystemInDarkTheme()) {
        ThemedContent(
            backgroundPainter = painterResource(id = R.drawable.cover_sky),
            terminalIconPainter = painterResource(id = R.drawable.ic_terminal_dark),
            findPersonIconPainter = painterResource(id = R.drawable.ic_loupe_dark),
            textIconPainter = painterResource(id = R.drawable.ic_text_dark),
            rotationTerminal = rotationTerminal,
            rotationLoupe = rotationLoupe,
            rotationText = rotationText,
            scrollState = scrollState
        )
    } else {
        ThemedContent(
            backgroundPainter = painterResource(id = R.drawable.cover_sky_light),
            terminalIconPainter = painterResource(id = R.drawable.ic_terminal_light),
            findPersonIconPainter = painterResource(id = R.drawable.ic_loupe_light),
            textIconPainter = painterResource(id = R.drawable.ic_text_light),
            rotationTerminal = rotationTerminal,
            rotationLoupe = rotationLoupe,
            rotationText = rotationText,
            scrollState = scrollState
        )
    }
}


@Composable
fun SignInContent(onNavigateToMyDevices: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(35.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        ZenitechLogo()
        SignInButton(onNavigateToMyDevices)
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun SignInButton(onNavigateToMyDevices: () -> Unit) {
    PrimaryButton(
        onClick = { onNavigateToMyDevices() },
        content = {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text("Sign in with Google", fontWeight = FontWeight.Bold)
        }
    )
}

@Composable
fun ZenitechLogo() {
    Image(
        modifier = Modifier.padding(20.dp, 10.dp),
        painter = painterResource(if(isSystemInDarkTheme())R.drawable.ic_zenitech_logo_white else R.drawable.ic_zenitech_logo_light),
        contentScale = ContentScale.Fit,
        contentDescription = null
    )
}
