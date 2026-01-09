package com.itechsolution.birrscan.ui.verification

import com.itechsolution.birrscan.domain.models.TransactionData

data class VerifyUiState(
    val isProcessing: Boolean = false,
    val isVerified: Boolean = false,
    var isSaved: Boolean = false,
    val extractedText: String? = null,
    val transactionData: TransactionData? = null,
    val error: String? = null
)

