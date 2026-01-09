package com.itechsolution.birrscan.utils
import android.graphics.*
object OcrImagePreprocessor {

    fun preprocess(bitmap: Bitmap): Bitmap {
        val grayscale = toGrayscale(bitmap)
        return increaseContrast(grayscale)
    }

    /**
     * Step 1: Convert to grayscale
     */
    private fun toGrayscale(src: Bitmap): Bitmap {
        val bmp = Bitmap.createBitmap(
            src.width,
            src.height,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bmp)
        val paint = Paint()

        val matrix = ColorMatrix().apply {
            setSaturation(0f)
        }

        paint.colorFilter = ColorMatrixColorFilter(matrix)
        canvas.drawBitmap(src, 0f, 0f, paint)

        return bmp
    }

    /**
     * Step 2: Increase contrast + slight brightness
     */
    private fun increaseContrast(src: Bitmap): Bitmap {
        val contrast = 1.5f     // 🔧 tweak: 1.3–1.8
        val brightness = -20f   // 🔧 tweak: -40 to 0

        val cm = ColorMatrix(
            floatArrayOf(
                contrast, 0f, 0f, 0f, brightness,
                0f, contrast, 0f, 0f, brightness,
                0f, 0f, contrast, 0f, brightness,
                0f, 0f, 0f, 1f, 0f
            )
        )

        val ret = Bitmap.createBitmap(
            src.width,
            src.height,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(ret)
        val paint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(cm)
        }

        canvas.drawBitmap(src, 0f, 0f, paint)

        return ret
    }
}