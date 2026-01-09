package com.itechsolution.birrscan.utils

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import kotlin.math.roundToInt

object FrameToBitmapCropper {

    fun crop(
        bitmap: Bitmap,
        previewSize: Size,
        frameRect: Rect
    ): Bitmap {

        val scaleX = bitmap.width / previewSize.width
        val scaleY = bitmap.height / previewSize.height

        val left = (frameRect.left * scaleX).roundToInt()
        val top = (frameRect.top * scaleY).roundToInt()
        val width = (frameRect.width * scaleX).roundToInt()
        val height = (frameRect.height * scaleY).roundToInt()

        return Bitmap.createBitmap(
            bitmap,
            left.coerceAtLeast(0),
            top.coerceAtLeast(0),
            width.coerceAtMost(bitmap.width - left),
            height.coerceAtMost(bitmap.height - top)
        )
    }
}
