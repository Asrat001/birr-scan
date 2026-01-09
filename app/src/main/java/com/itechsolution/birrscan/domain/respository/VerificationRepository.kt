package com.itechsolution.birrscan.domain.respository

import com.itechsolution.birrscan.domain.models.TransactionData
import java.io.File

interface VerificationRepository {
   suspend fun  processImage(imageFile: File): Result<String>
   suspend fun saveReceipt( transaction: TransactionData):Result<Boolean>
}