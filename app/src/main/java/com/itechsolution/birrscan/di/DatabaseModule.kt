package com.itechsolution.birrscan.di


import android.content.Context
import androidx.room.Room
import com.itechsolution.birrscan.data.local.AppDatabase
import com.itechsolution.birrscan.data.local.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "my_app_db"
        ).build()

    @Provides
    fun provideTransactionDao(
        db: AppDatabase
    ): TransactionDao = db.transactionDao()
}
