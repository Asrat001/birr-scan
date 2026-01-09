package com.itechsolution.birrscan.utils

import android.util.Log
import com.itechsolution.birrscan.domain.models.TransactionData

object TelebirrTransactionParser : BankTransactionParser {
    override fun parse(ocrText: String): TransactionData {
        val lines = ocrText.lines().map { it.trim() }.filter { it.isNotEmpty() }
        Log.d("TelebirrTransactionParser", "Lines: $lines")

        // AMOUNT (remove minus + junk)
        val amount = lines
            .firstOrNull { it.contains(Regex("\\d+\\.\\d+")) }
            ?.replace("[^0-9.]".toRegex(), "")
            ?.toDoubleOrNull()

        // TRANSACTION ID (Telebirr format)
        val transactionId = lines
            .firstOrNull { it.matches(Regex("[A-Z0-9]{8,}")) }


        return TransactionData(
            amount = amount,
            transactionId = transactionId,
            currency = "ETB",
            institution = "Tele Birr"
        )
    }
}
