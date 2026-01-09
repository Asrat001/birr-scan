package com.itechsolution.birrscan.ui.componets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ReceiptFrameOverlay(
    modifier: Modifier = Modifier,
    frameWidthRatio: Float = 0.9f,
    frameHeightRatio: Float = 0.4f,
    onFrameReady: (Rect, Size) -> Unit   // 👈 NEW
) {
    Canvas(modifier = modifier.fillMaxSize()) {

        val frameWidth = size.width * frameWidthRatio
        val frameHeight = size.height * frameHeightRatio

        val left = (size.width - frameWidth) / 2
        val top = (size.height - frameHeight) / 2

        val frameRect = Rect(
            left,
            top,
            left + frameWidth,
            top + frameHeight
        )

        // 🔥 report frame + preview size
        onFrameReady(frameRect, size)

        // Dark overlay
        drawRect(
            color = Color.Black.copy(alpha = 0.55f),
            size = size
        )

        // Clear center
        drawRect(
            color = Color.Transparent,
            topLeft = Offset(frameRect.left, frameRect.top),
            size = Size(frameRect.width, frameRect.height),
            blendMode = BlendMode.Clear
        )

        // Frame border
        drawRect(
            color = Color.Green.copy(alpha = 0.35f),
            topLeft = Offset(frameRect.left, frameRect.top),
            size = Size(frameRect.width, frameRect.height),
            style = Stroke(width = 3.dp.toPx())
        )
    }
}