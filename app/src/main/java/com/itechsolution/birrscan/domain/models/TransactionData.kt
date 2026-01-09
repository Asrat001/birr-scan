package com.itechsolution.birrscan.domain.models

data class TransactionData(
    val amount: Double?,
    val transactionId: String?,
    val currency: String="ETB",
    val institution: String?,
    val timestamp: Long = System.currentTimeMillis()
)
