package com.itechsolution.birrscan.di



import com.itechsolution.birrscan.data.repository.VerificationRepositoryImpl
import com.itechsolution.birrscan.domain.respository.VerificationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class VerificationModule {

    @Binds
    @Singleton
    abstract fun bindVerificationRepository(
        impl: VerificationRepositoryImpl
    ): VerificationRepository
}
