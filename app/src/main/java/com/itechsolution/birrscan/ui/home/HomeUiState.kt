package com.itechsolution.birrscan.ui.home


import com.itechsolution.birrscan.domain.models.TransactionData
import com.itechsolution.birrscan.utils.TimeFilter

data class HomeUiState(
    val isLoading: Boolean = false,
    val transactions: List<TransactionData> = emptyList(),
    val totalAmount: Double = 0.0,
    val selectedFilter: TimeFilter = TimeFilter.Today,
    val error: String? = null
)
