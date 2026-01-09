package com.itechsolution.birrscan.utils

import com.itechsolution.birrscan.domain.models.TransactionData

interface BankTransactionParser {
    fun parse(ocrText: String): TransactionData
}