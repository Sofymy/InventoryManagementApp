package com.zenitech.imaapp.feature.qr_reader

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.core.Camera
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.FlashlightOff
import androidx.compose.material.icons.twotone.FlashlightOn
import androidx.compose.material.icons.twotone.ZoomIn
import androidx.compose.material.icons.twotone.ZoomOut
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.InstructionsComponent
import com.zenitech.imaapp.ui.common.RoundedButton
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import java.util.concurrent.Executors

@Composable
fun QRReaderScreen() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        QRReaderContent()
    }
}

@Composable
fun QRReaderContent() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QRReaderSquare()
    }
}


@androidx.annotation.OptIn(ExperimentalGetImage::class)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QRReaderSquare() {
    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)
    val camera = remember { BarcodeCamera() }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val showBarCodeSquare = remember { mutableStateOf(false) }

    QRReaderHandlePermissionAndLifecycle(
        context = context,
        cameraPermission = cameraPermission,
        camera = camera,
        lifecycleOwner = lifecycleOwner,
        onShowBarCodeSquareChange = {
            showBarCodeSquare.value = it
        }
    )

    if (cameraPermission.status.isGranted) {
        if (showBarCodeSquare.value) {
            QRReaderSquareContent(
                camera = camera,
            )
        }
    }
}

@androidx.annotation.OptIn(ExperimentalGetImage::class)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QRReaderHandlePermissionAndLifecycle(
    cameraPermission: PermissionState,
    camera: BarcodeCamera,
    lifecycleOwner: LifecycleOwner,
    context: Context,
    onShowBarCodeSquareChange: (Boolean) -> Unit
) {
    DisposableEffect(Unit) {
        if (!cameraPermission.status.isGranted) {
            cameraPermission.launchPermissionRequest()
        }

        onDispose { camera.unbindCamera(context) }
    }

    if (cameraPermission.status.isGranted) {
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> onShowBarCodeSquareChange(true)
                    Lifecycle.Event.ON_PAUSE -> onShowBarCodeSquareChange(false)
                    else -> {}
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }
    else{
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.this_feature_is_unavailable_because_it_requires_camera_permission),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
fun QRReaderSquareContent(
    camera: BarcodeCamera,
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val color = MaterialTheme.colorScheme.primary
    val borderColor = LocalCardColorsPalette.current.borderColor

    var lastScannedBarcode by remember { mutableStateOf<String?>(null) }
    val isToggleOn = remember { mutableStateOf(false) }
    val zoomLevel = remember { mutableFloatStateOf(1f) }

    InstructionsComponent(
        "Place the QR code of your device in the frame",
        modifier = Modifier.padding(15.dp)
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        camera.CameraPreview(
            onBarcodeScanned = {
                if (it != null) {
                    lastScannedBarcode = it.displayValue
                }
            },
            lifecycleOwner = LocalLifecycleOwner.current,
        )

        QRReaderSquareCanvas(
            backgroundColor = backgroundColor,
            borderColor = borderColor,
            color = color
        )

        Column(
            modifier = Modifier
                .padding(30.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QRReaderLastScannedBarcode(lastScannedBarcode)
            Row {
                RoundedButton(
                    onClick = {
                        val newState = !isToggleOn.value
                        camera.toggleFlash(newState)
                        isToggleOn.value = newState
                    },
                    iconImageVector = if (isToggleOn.value) Icons.TwoTone.FlashlightOff else Icons.TwoTone.FlashlightOn,
                )
                Spacer(modifier = Modifier.width(10.dp))
                RoundedButton(
                    onClick = {
                        zoomLevel.floatValue += 1f
                        camera.zoom(zoomLevel.floatValue)
                    },
                    enabled = zoomLevel.floatValue <= 4,
                    iconImageVector = Icons.TwoTone.ZoomIn
                )
                Spacer(modifier = Modifier.width(10.dp))
                RoundedButton(
                    onClick = {
                        zoomLevel.floatValue -= 1f
                        camera.zoom(zoomLevel.floatValue)
                    },
                    iconImageVector = Icons.TwoTone.ZoomOut,
                    enabled = zoomLevel.floatValue >= 2,
                )
            }
        }
    }
}



@Composable
fun QRReaderSquareCanvas(
    backgroundColor: Color,
    borderColor: Color,
    color: Color
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val cornerLength = 20.dp.toPx()
        val strokeWidth = 8.dp.toPx()

        val width = canvasWidth * 0.8f

        val topLeftX = (canvasWidth - width) / 2
        val topLeftY = canvasHeight * 0.1f

        drawRect(color = backgroundColor)

        // Draws the rectangle in the middle
        drawRoundRect(
            topLeft = Offset(topLeftX, topLeftY),
            size = Size(width, width),
            color = Color.Transparent,
            cornerRadius = CornerRadius(15.dp.toPx()),
            blendMode = BlendMode.SrcIn
        )

        drawRect(
            topLeft = Offset(topLeftX, topLeftY),
            color = borderColor,
            size = Size(width, width),
            style = Stroke(width = 1.dp.toPx()),
            blendMode = BlendMode.Src
        )

        // Draws the rectangle outline
        // Top-left corner
        drawLine(
            color = color,
            start = Offset(topLeftX - (strokeWidth / 2), topLeftY),
            end = Offset(topLeftX + cornerLength, topLeftY),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = color,
            start = Offset(topLeftX, topLeftY),
            end = Offset(topLeftX, topLeftY + cornerLength),
            strokeWidth = strokeWidth
        )

        // Top-right corner
        drawLine(
            color = color,
            start = Offset(topLeftX + width + (strokeWidth / 2), topLeftY),
            end = Offset(topLeftX + width - cornerLength, topLeftY),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = color,
            start = Offset(topLeftX + width, topLeftY),
            end = Offset(topLeftX + width, topLeftY + cornerLength),
            strokeWidth = strokeWidth
        )

        // Bottom-left corner
        drawLine(
            color = color,
            start = Offset(topLeftX - (strokeWidth / 2), topLeftY + width),
            end = Offset(topLeftX + cornerLength, topLeftY + width),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = color,
            start = Offset(topLeftX, topLeftY + width),
            end = Offset(topLeftX, topLeftY + width - cornerLength),
            strokeWidth = strokeWidth
        )

        // Bottom-right corner
        drawLine(
            color = color,
            start = Offset(topLeftX + width + (strokeWidth / 2), topLeftY + width),
            end = Offset(topLeftX + width - cornerLength, topLeftY + width),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = color,
            start = Offset(topLeftX + width, topLeftY + width),
            end = Offset(topLeftX + width, topLeftY + width - cornerLength),
            strokeWidth = strokeWidth
        )
    }
}

@Composable
fun QRReaderLastScannedBarcode(
    lastScannedBarcode: String?,
) {
    AnimatedVisibility(
        visible = lastScannedBarcode != null,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        Text(
            text = lastScannedBarcode.toString(),
            modifier = Modifier
                .padding(16.dp),
        )
    }

}

@ExperimentalGetImage
class BarcodeCamera {

    private var camera: Camera? = null
    private var cameraControl: CameraControl? = null

    @Composable
    fun CameraPreview(
        onBarcodeScanned: (Barcode?) -> Unit,
        lifecycleOwner: LifecycleOwner,
    ) {
        val imageCapture = remember {
            ImageCapture
                .Builder()
                .build()
        }

        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    layoutParams =
                        LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    scaleType = PreviewView.ScaleType.FILL_START

                    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                    cameraProviderFuture.addListener({
                        startCamera(
                            context = ctx,
                            previewView = this,
                            imageCapture = imageCapture,
                            lifecycleOwner = lifecycleOwner,
                            onBarcodeScanned = onBarcodeScanned
                        )
                    }, ContextCompat.getMainExecutor(ctx))
                }
            }
        )
    }

    private fun startCamera(
        context: Context,
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
        imageCapture: ImageCapture,
        onBarcodeScanned: (Barcode?) -> Unit
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.surfaceProvider = previewView.surfaceProvider
                }

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(
                        Executors.newSingleThreadExecutor()
                    ) { imageProxy ->
                        processImageProxy(
                            imageProxy,
                            onBarcodeScanned
                        )
                    }
                }

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture,
                    imageAnalysis
                )

                // Initialize camera control
                cameraControl = camera?.cameraControl

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(context))
    }

    private fun processImageProxy(
        imageProxy: ImageProxy,
        onBarcodeScanned: (Barcode?) -> Unit
    ) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_ALL_FORMATS
                )
                .build()

            val scanner: BarcodeScanner = BarcodeScanning.getClient(options)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        onBarcodeScanned(barcode)
                    }
                }
                .addOnFailureListener {
                    Log.e(TAG, "Barcode processing failed", it)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

    fun unbindCamera(
        context: Context
    ) {
        camera?.let {
            val cameraProvider = ProcessCameraProvider.getInstance(context).get()
            cameraProvider.unbindAll()
        }
    }

    fun toggleFlash(
        isOn: Boolean,
    ) {
        camera?.cameraControl?.enableTorch(isOn)
    }

    fun zoom(
        zoomLevel: Float
    ) {

        val minZoom = camera?.cameraInfo?.zoomState?.value?.minZoomRatio ?: 1f
        val maxZoom = camera?.cameraInfo?.zoomState?.value?.maxZoomRatio ?: 5f

        val clampedZoomLevel = zoomLevel.coerceIn(minZoom, maxZoom)
        cameraControl?.setZoomRatio(clampedZoomLevel)
    }

}

