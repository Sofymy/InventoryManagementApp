package com.zenitech.imaapp.domain.di

import com.zenitech.imaapp.data.repository.AdminRepository
import com.zenitech.imaapp.data.repository.MyDevicesRepository
import com.zenitech.imaapp.data.repository.QRReaderRepository
import com.zenitech.imaapp.data.repository.RequestRepository
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import com.zenitech.imaapp.domain.usecases.my_devices.LoadMyDevicesUseCase
import com.zenitech.imaapp.domain.usecases.my_devices.MyDevicesUseCases
import com.zenitech.imaapp.domain.usecases.qr_reader.QRReaderUseCases
import com.zenitech.imaapp.domain.usecases.request.RequestUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideMyDevicesUseCases(
        repository: MyDevicesRepository,
        loadMyDevices: LoadMyDevicesUseCase
    ): MyDevicesUseCases = MyDevicesUseCases(repository, loadMyDevices)

    @Provides
    @Singleton
    fun provideLoadMyDevicesUseCase(
        repository: MyDevicesRepository
    ): LoadMyDevicesUseCase = LoadMyDevicesUseCase(repository)


    @Provides
    @Singleton
    fun provideAdminUseCases(
        repository: AdminRepository,
    ): AdminUseCases = AdminUseCases(repository)


    @Provides
    @Singleton
    fun provideQRReaderUseCases(
        repository: QRReaderRepository,
    ): QRReaderUseCases = QRReaderUseCases(repository)


    @Provides
    @Singleton
    fun provideRequestUseCases(
        repository: RequestRepository,
    ): RequestUseCases = RequestUseCases(repository)
}

