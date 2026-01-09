package com.itechsolution.birrscan.domain.respository

import com.itechsolution.birrscan.domain.models.TransactionData
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getTodayFlow(start: Long, end: Long): Flow<List<TransactionData>>
    fun getLast7DaysFlow(from: Long): Flow<List<TransactionData>>
    fun getCustomRangeFlow(from: Long, to: Long): Flow<List<TransactionData>>
}



