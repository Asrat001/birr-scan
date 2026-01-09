package com.itechsolution.birrscan.utils

import com.itechsolution.birrscan.domain.models.TransactionData

object CbeTransactionParser : BankTransactionParser {
    override fun parse(ocrText: String): TransactionData {
        val amountRegex = Regex("""ETB\s*([\d,]+\.\d{1,2})""", RegexOption.IGNORE_CASE)
        val idRegex = Regex("""transaction ID[:\s]*([A-Z0-9]{8,12})""", RegexOption.IGNORE_CASE)

        val amount = amountRegex.find(ocrText)?.groups?.get(1)?.value
            ?.replace(",", "")?.toDoubleOrNull()
        val transactionId = idRegex.find(ocrText)?.groups?.get(1)?.value?.trim()

        return TransactionData(
            amount = amount,
            transactionId = transactionId,
            currency = "ETB",
            institution = "CBE"
        )
    }
}
