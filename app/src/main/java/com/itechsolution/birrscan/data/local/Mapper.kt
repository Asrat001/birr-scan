package com.itechsolution.birrscan.data.local

import com.itechsolution.birrscan.domain.models.TransactionData

fun TransactionData.toEntity(): TransactionEntity {
    return TransactionEntity(
        amount = amount ?: error("Amount cannot be null"),
        institution = institution ?: "Unknown",
        transactionId = transactionId ?: "Unknown"
    )
}

fun TransactionEntity.toDomain(): TransactionData {
    return TransactionData(
        amount = amount,
        transactionId = transactionId,
        institution = institution,
    )
}