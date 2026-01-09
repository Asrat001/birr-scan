package com.itechsolution.birrscan.data.repository

import android.graphics.BitmapFactory
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.itechsolution.birrscan.data.local.TransactionDao
import com.itechsolution.birrscan.data.local.toEntity
import com.itechsolution.birrscan.domain.models.TransactionData
import com.itechsolution.birrscan.domain.respository.VerificationRepository
import java.io.File
import javax.inject.Inject

class VerificationRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
) : VerificationRepository {

    override suspend fun processImage(imageFile: File): Result<String> {
        return try {
            val bitmap = BitmapFactory.decodeFile(imageFile.path)
            val inputImage = InputImage.fromBitmap(bitmap, 0)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val result = kotlinx.coroutines.suspendCancellableCoroutine<String> { cont ->
                recognizer.process(inputImage)
                    .addOnSuccessListener { visionText ->
                        cont.resume(visionText.text) { cause, _, _ -> }
                    }
                    .addOnFailureListener { e ->
                        cont.resumeWith(Result.failure(e))
                    }
            }
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveReceipt(
        transaction: TransactionData
    ): Result<Boolean> {
        return try {
            transactionDao.insertTransaction(transaction.toEntity())
            Result.success(true)
        } catch (e: Exception) {
            Log.e("VerificationRepo", "Save failed", e)
            Result.failure(e)
        }
    }

}