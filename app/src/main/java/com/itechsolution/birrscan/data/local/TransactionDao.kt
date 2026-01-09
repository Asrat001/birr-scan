package com.itechsolution.birrscan.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    // Listen to all transactions
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getTransactions(): Flow<List<TransactionEntity>>

    // Today transactions
    @Query("""
        SELECT * FROM transactions
        WHERE timestamp BETWEEN :start AND :end
        ORDER BY timestamp DESC
    """)
    fun getToday(
        start: Long,
        end: Long
    ): Flow<List<TransactionEntity>>

    // Last 7 days transactions
    @Query("""
        SELECT * FROM transactions
        WHERE timestamp >= :from
        ORDER BY timestamp DESC
    """)
    fun getLast7Days(from: Long): Flow<List<TransactionEntity>>

    // Custom date range
    @Query("""
        SELECT * FROM transactions
        WHERE timestamp BETWEEN :from AND :to
        ORDER BY timestamp DESC
    """)
    fun getByDateRange(
        from: Long,
        to: Long
    ): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions")
    suspend fun clearAll()
}
