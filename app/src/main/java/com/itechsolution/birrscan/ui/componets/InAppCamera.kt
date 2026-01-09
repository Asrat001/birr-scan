package com.itechsolution.birrscan.ui.componets

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.itechsolution.birrscan.utils.FrameToBitmapCropper
import com.itechsolution.birrscan.utils.OcrImagePreprocessor
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun InAppCamera(onImageCaptured: (File) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = context as LifecycleOwner
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    var frameRect by remember { mutableStateOf<Rect?>(null) }
    var previewSize by remember { mutableStateOf<Size?>(null) }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Log.e("Camera", "Camera permission denied")
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    Box(modifier = Modifier.fillMaxSize()) {



        // 📸 Camera Preview
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)

                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    imageCapture = ImageCapture.Builder().build()

                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        imageCapture
                    )
                }, ContextCompat.getMainExecutor(ctx))

                previewView
            },
            modifier = Modifier.fillMaxSize()
        )
        // 🧾 Overlay frame
        ReceiptFrameOverlay(
            onFrameReady = { rect, size ->
                frameRect = rect
                previewSize = size
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        CameraControls(
            onImageCaptured = {
                val file = File(
                    context.cacheDir,
                    SimpleDateFormat(
                        "yyyy-MM-dd-HH-mm-ss-SSS",
                        Locale.US
                    ).format(System.currentTimeMillis()) + ".jpg"
                )

                imageCapture?.takePicture(
                    ImageCapture.OutputFileOptions.Builder(file).build(),
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(
                            output: ImageCapture.OutputFileResults
                        ) {
                            val bitmap = BitmapFactory.decodeFile(file.absolutePath)

                            val cropped = FrameToBitmapCropper.crop(
                                bitmap = bitmap,
                                previewSize = previewSize!!,
                                frameRect = frameRect!!
                            )

                            val processed = OcrImagePreprocessor.preprocess(cropped)

                            // overwrite file with cropped bitmap
                            file.outputStream().use {
                                processed.compress(Bitmap.CompressFormat.JPEG, 100, it)
                            }

                            onImageCaptured(file)
                        }

                        override fun onError(
                            exception: ImageCaptureException
                        ) {
                            Log.e(
                                "Camera",
                                "Capture failed",
                                exception
                            )
                        }
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.White)
                .padding(bottom = 36.dp)
        )

    }
}
