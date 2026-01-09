package com.itechsolution.birrscan.data.repository


import com.itechsolution.birrscan.data.local.TransactionDao
import com.itechsolution.birrscan.data.local.toDomain
import com.itechsolution.birrscan.domain.models.TransactionData
import com.itechsolution.birrscan.domain.respository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : HomeRepository {

    override fun getTodayFlow(start: Long, end: Long): Flow<List<TransactionData>> {
        return dao.getToday(start, end).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getLast7DaysFlow(from: Long): Flow<List<TransactionData>> {
        return dao.getLast7Days(from).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getCustomRangeFlow(from: Long, to: Long): Flow<List<TransactionData>> {
        return dao.getByDateRange(from, to).map { list ->
            list.map { it.toDomain() }
        }
    }
}
