package com.itechsolution.birrscan.data.local
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    indices = [Index(value = ["timestamp"])]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val institution: String,
    val transactionId: String,
    val amount: Double,
    val timestamp: Long = System.currentTimeMillis()
)
