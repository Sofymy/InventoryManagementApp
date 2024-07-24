package com.zenitech.imaapp.data.di

import androidx.appcompat.app.AppCompatActivity
import com.zenitech.imaapp.data.repository.AdminRepository
import com.zenitech.imaapp.data.repository.AdminRepositoryImpl
import com.zenitech.imaapp.data.repository.DeviceDetailsRepository
import com.zenitech.imaapp.data.repository.DeviceDetailsRepositoryImpl
import com.zenitech.imaapp.data.repository.DeviceListRepository
import com.zenitech.imaapp.data.repository.DeviceListRepositoryImpl
import com.zenitech.imaapp.data.repository.MyDevicesRepository
import com.zenitech.imaapp.data.repository.MyDevicesRepositoryImpl
import com.zenitech.imaapp.data.repository.QRReaderRepository
import com.zenitech.imaapp.data.repository.QRReaderRepositoryImpl
import com.zenitech.imaapp.data.repository.RequestRepository
import com.zenitech.imaapp.data.repository.RequestRepositoryImpl
import com.zenitech.imaapp.data.repository.SignInRepository
import com.zenitech.imaapp.data.repository.SignInRepositoryImpl
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
    fun providesMyDevicesRepository(): MyDevicesRepository = MyDevicesRepositoryImpl()

    @Provides
    @Singleton
    fun providesDeviceListRepository(): DeviceListRepository = DeviceListRepositoryImpl()

    @Provides
    @Singleton
    fun providesAdminRepository(): AdminRepository = AdminRepositoryImpl()

    @Provides
    @Singleton
    fun providesQRReaderRepository(): QRReaderRepository = QRReaderRepositoryImpl()

    @Provides
    @Singleton
    fun providesRequestRepository(): RequestRepository = RequestRepositoryImpl()

    @Provides
    @Singleton
    fun provideSignInRepository(): SignInRepository = SignInRepositoryImpl()

    @Provides
    @Singleton
    fun provideDeviceDetailsRepository(): DeviceDetailsRepository = DeviceDetailsRepositoryImpl()

}