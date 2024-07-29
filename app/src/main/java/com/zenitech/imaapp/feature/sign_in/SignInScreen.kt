package com.zenitech.imaapp.feature.sign_in

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.CircularLoadingIndicator
import com.zenitech.imaapp.ui.common.PrimaryButton
import com.zenitech.imaapp.ui.utils.GoogleSignInHelper
import kotlinx.coroutines.launch


@Composable
fun SignInScreen(
    onNavigateToMyDevices: () -> Unit
) {
    SignInAnimatedBackground()
    SignInContent(onNavigateToMyDevices)
}

@Composable
fun SignInAnimatedBackground(){
    val rotationTerminal = remember { Animatable(-30f) }
    val rotationLoupe = remember { Animatable(10f) }
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
                targetValue = -10f,
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
                    animation = tween(durationMillis = 8000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
    }

    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState.isScrollInProgress) {
        scrollState.animateScrollTo(scrollState.value + 1, spring(stiffness = Spring.StiffnessHigh))
    }

    SignInAnimatedThemedBackground(
        rotationTerminal = rotationTerminal.value,
        rotationLoupe = rotationLoupe.value,
        rotationText = rotationText.value,
        scrollState = scrollState
    )
}

@Composable
fun SignInAnimatedThemedBackground(
    scrollState: ScrollState,
    rotationTerminal: Float,
    rotationLoupe: Float,
    rotationText: Float
) {
    if (isSystemInDarkTheme()) {
        SignInAnimatedThemedBackgroundContent(
            backgroundPainter = painterResource(id = R.drawable.cover_sky),
            terminalIconPainter = painterResource(id = R.drawable.ic_terminal_dark),
            loupeIconPainter = painterResource(id = R.drawable.ic_loupe_dark),
            textIconPainter = painterResource(id = R.drawable.ic_text_dark),
            rotationTerminal = rotationTerminal,
            rotationLoupe = rotationLoupe,
            rotationText = rotationText,
            scrollState = scrollState
        )
    } else {
        SignInAnimatedThemedBackgroundContent(
            backgroundPainter = painterResource(id = R.drawable.cover_sky_light),
            terminalIconPainter = painterResource(id = R.drawable.ic_terminal_light),
            loupeIconPainter = painterResource(id = R.drawable.ic_loupe_light),
            textIconPainter = painterResource(id = R.drawable.ic_text_light),
            rotationTerminal = rotationTerminal,
            rotationLoupe = rotationLoupe,
            rotationText = rotationText,
            scrollState = scrollState
        )
    }
}

@Composable
fun SignInAnimatedThemedBackgroundContent(
    scrollState: ScrollState,
    backgroundPainter: Painter,
    terminalIconPainter: Painter,
    loupeIconPainter: Painter,
    textIconPainter: Painter,
    rotationTerminal: Float,
    rotationLoupe: Float,
    rotationText: Float
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .horizontalScroll(scrollState)
            .paint(
                painter = backgroundPainter,
                alignment = Alignment.TopStart,
                contentScale = ContentScale.FillHeight,
            )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.77f)
        ,
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = terminalIconPainter,
                contentDescription = null,
                modifier = Modifier
                    .padding(20.dp, end = 40.dp, top = 10.dp)
                    .graphicsLayer {
                        rotationZ = rotationTerminal
                    }
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = loupeIconPainter,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 15.dp)
                    .graphicsLayer {
                        rotationZ = rotationLoupe
                    }
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = textIconPainter,
                contentScale = ContentScale.FillHeight,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 30.dp)
                    .graphicsLayer {
                        rotationZ = rotationText
                    }
            )
        }
    }
}


@Composable
fun SignInContent(
    onNavigateToMyDevices: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val isLoading = remember {
        mutableStateOf(false)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onEvent(SignInUserEvent.HasUser)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(state) {
        state.let {
            when (it) {
                SignInState.Success -> {
                    onNavigateToMyDevices()
                }
                SignInState.Loading -> {
                    isLoading.value = true
                }
                else -> {
                    Log.e("SignInButton", "Google sign-in failed:")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(35.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        ZenitechLogo()
        SignInButton(onNavigateToMyDevices, isLoading = isLoading)
        Spacer(modifier = Modifier.height(20.dp))
    }

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

@Composable
fun SignInButton(
    onNavigateToMyDevices: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel(),
    isLoading: MutableState<Boolean>
) {
    val context = LocalContext.current

    val client = remember { GoogleSignInHelper.getGoogleSignInClient(context) }
    val request = remember { GoogleSignInHelper.getGoogleSignInRequest() }

    val signInResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if(result.resultCode == Activity.RESULT_OK && result.data != null) {
            val credential = client.getSignInCredentialFromIntent(result.data)
            val idToken = credential.googleIdToken

            if(idToken != null) {
                viewModel.onEvent(SignInUserEvent.SignIn(idToken))
            } else {
                Log.e("SignInButton", "Google sign-in failed: idToken is null")
            }
        } else {
            Log.e("SignInButton", "Google sign-in failed: Activity result not OK")
        }
    }

    PrimaryButton(
        onClick = {
            client.beginSignIn(request).addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    val intentSender = task.result.pendingIntent.intentSender
                    val intentSenderRequest = IntentSenderRequest.Builder(intentSender).build()
                    signInResultLauncher.launch(intentSenderRequest)
                } else {
                    Log.e("SignInButton", "Google sign-in initiation failed: ${task.exception?.message}")
                }
            }
        },
        content = {
            if(isLoading.value){
                CircularLoadingIndicator(size = 20.dp)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularLoadingIndicator(size = 20.dp, strokeWidth = 2.dp)
                }
            }
            else {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.ic_google_logo),
                        contentScale = ContentScale.Fit,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Sign in with Google", fontWeight = FontWeight.Bold)
                }
            }
        }
    )
}


