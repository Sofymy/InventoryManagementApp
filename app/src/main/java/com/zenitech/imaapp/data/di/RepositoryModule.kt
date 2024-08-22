package com.zenitech.imaapp.data.di

import com.zenitech.imaapp.data.repository.AdminRepository
import com.zenitech.imaapp.data.repository.AdminRepositoryImpl
import com.zenitech.imaapp.data.repository.DeviceDetailsRepository
import com.zenitech.imaapp.data.repository.DeviceDetailsRepositoryImpl
import com.zenitech.imaapp.data.repository.MyDevicesRepository
import com.zenitech.imaapp.data.repository.MyDevicesRepositoryImpl
import com.zenitech.imaapp.data.repository.QRReaderRepository
import com.zenitech.imaapp.data.repository.QRReaderRepositoryImpl
import com.zenitech.imaapp.data.repository.RequestTestDeviceRepository
import com.zenitech.imaapp.data.repository.RequestTestDeviceRepositoryImpl
import com.zenitech.imaapp.data.auth.AuthenticationService
import com.zenitech.imaapp.data.auth.AuthenticationServiceImpl
import com.zenitech.imaapp.data.network.api.DeviceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesMyDevicesRepository(
        deviceApi: DeviceApi
    ): MyDevicesRepository = MyDevicesRepositoryImpl(deviceApi)

    @Provides
    @Singleton
    fun providesAdminRepository(): AdminRepository = AdminRepositoryImpl()

    @Provides
    @Singleton
    fun providesQRReaderRepository(): QRReaderRepository = QRReaderRepositoryImpl()

    @Provides
    @Singleton
    fun providesRequestTestDeviceRepository(): RequestTestDeviceRepository = RequestTestDeviceRepositoryImpl()

    @Provides
    @Singleton
    fun provideDeviceDetailsRepository(): DeviceDetailsRepository = DeviceDetailsRepositoryImpl()

}