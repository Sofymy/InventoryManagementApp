package com.zenitech.imaapp.domain.di

import com.zenitech.imaapp.data.repository.AdminRepository
import com.zenitech.imaapp.data.repository.DeviceDetailsRepository
import com.zenitech.imaapp.data.repository.MyDevicesRepository
import com.zenitech.imaapp.data.repository.QRReaderRepository
import com.zenitech.imaapp.data.repository.RequestTestDeviceRepository
import com.zenitech.imaapp.data.auth.AuthenticationService
import com.zenitech.imaapp.domain.usecases.admin.AdminCreateDeviceUseCase
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import com.zenitech.imaapp.domain.usecases.admin.LoadAdminAddDeviceAssetsUseCase
import com.zenitech.imaapp.domain.usecases.admin.LoadAdminAddDeviceManufacturersUseCase
import com.zenitech.imaapp.domain.usecases.admin.LoadAdminAddDeviceSitesUseCase
import com.zenitech.imaapp.domain.usecases.admin.LoadAdminAddDeviceTypesUseCase
import com.zenitech.imaapp.domain.usecases.admin.LoadAdminDevicesUseCase
import com.zenitech.imaapp.domain.usecases.device_details.DeviceDetailsUseCases
import com.zenitech.imaapp.domain.usecases.device_details.LoadDeviceDetailsUseCase
import com.zenitech.imaapp.domain.usecases.my_devices.LoadMyDevicesUseCase
import com.zenitech.imaapp.domain.usecases.my_devices.MyDevicesUseCases
import com.zenitech.imaapp.domain.usecases.qr_reader.QRReaderUseCases
import com.zenitech.imaapp.domain.usecases.request_test_device.LoadTestDeviceManufacturersUseCase
import com.zenitech.imaapp.domain.usecases.request_test_device.LoadTestDeviceTypesUseCase
import com.zenitech.imaapp.domain.usecases.request_test_device.RequestTestDeviceUseCases
import com.zenitech.imaapp.domain.usecases.request_test_device.SaveTestDeviceRequestUseCase
import com.zenitech.imaapp.domain.usecases.sign_in.HasUserUseCase
import com.zenitech.imaapp.domain.usecases.sign_in.SignInUseCases
import com.zenitech.imaapp.domain.usecases.sign_in.SignInWithGoogleUseCase
import com.zenitech.imaapp.domain.usecases.sign_in.SignOutUseCase
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
    fun provideDeviceDetailsUseCase(
        repository: DeviceDetailsRepository,
        loadDeviceDetails: LoadDeviceDetailsUseCase
    ): DeviceDetailsUseCases = DeviceDetailsUseCases(repository, loadDeviceDetails)

    @Provides
    @Singleton
    fun provideLoadDeviceDetailsUseCase(
        repository: DeviceDetailsRepository
    ): LoadDeviceDetailsUseCase = LoadDeviceDetailsUseCase(repository)

    @Provides
    @Singleton
    fun provideAdminUseCases(
        repository: AdminRepository,
        loadAdminDevices: LoadAdminDevicesUseCase,
        loadAdminAddDeviceAssets: LoadAdminAddDeviceAssetsUseCase,
        loadAdminAddDeviceTypes: LoadAdminAddDeviceTypesUseCase,
        loadAdminAddDeviceManufacturers: LoadAdminAddDeviceManufacturersUseCase,
        loadAdminAddDevicesSites: LoadAdminAddDeviceSitesUseCase,
        createDevice: AdminCreateDeviceUseCase
    ): AdminUseCases = AdminUseCases(repository, loadAdminDevices, loadAdminAddDeviceAssets, loadAdminAddDeviceTypes, loadAdminAddDeviceManufacturers, loadAdminAddDevicesSites, createDevice)

    @Provides
    @Singleton
    fun provideLoadAdminDevicesUseCase(
        repository: AdminRepository
    ): LoadAdminDevicesUseCase = LoadAdminDevicesUseCase(repository)

    @Provides
    @Singleton
    fun provideLoadAdminDeviceAssetsUseCase(
        repository: AdminRepository
    ): LoadAdminAddDeviceAssetsUseCase = LoadAdminAddDeviceAssetsUseCase(repository)

    @Provides
    @Singleton
    fun provideLoadAdminDeviceSitesUseCase(
        repository: AdminRepository
    ): LoadAdminAddDeviceSitesUseCase = LoadAdminAddDeviceSitesUseCase(repository)


    @Provides
    @Singleton
    fun provideLoadAdminDeviceTypesUseCase(
        repository: AdminRepository
    ): LoadAdminAddDeviceTypesUseCase = LoadAdminAddDeviceTypesUseCase(repository)

    @Provides
    @Singleton
    fun provideLoadAdminDeviceManufacturersUseCase(
        repository: AdminRepository
    ): LoadAdminAddDeviceManufacturersUseCase = LoadAdminAddDeviceManufacturersUseCase(repository)

    @Provides
    @Singleton
    fun provideAdminCreateDeviceUseCase(
        repository: AdminRepository
    ): AdminCreateDeviceUseCase = AdminCreateDeviceUseCase(repository)

    @Provides
    @Singleton
    fun provideQRReaderUseCases(
        repository: QRReaderRepository,
    ): QRReaderUseCases = QRReaderUseCases(repository)


    @Provides
    @Singleton
    fun provideRequestTestDeviceUseCases(
        repository: RequestTestDeviceRepository,
        loadTestDeviceTypesUseCase: LoadTestDeviceTypesUseCase,
        loadTestDeviceManufacturers: LoadTestDeviceManufacturersUseCase,
        saveTestDeviceRequest: SaveTestDeviceRequestUseCase
    ): RequestTestDeviceUseCases = RequestTestDeviceUseCases(repository, loadTestDeviceManufacturers, loadTestDeviceTypesUseCase, saveTestDeviceRequest)


    @Provides
    @Singleton
    fun provideLoadTestDeviceTypesUseCase(
        repository: RequestTestDeviceRepository,
    ): LoadTestDeviceTypesUseCase = LoadTestDeviceTypesUseCase(
        repository
    )

    @Provides
    @Singleton
    fun provideLoadTestDeviceManufacturersUseCase(
        repository: RequestTestDeviceRepository,
    ): LoadTestDeviceManufacturersUseCase = LoadTestDeviceManufacturersUseCase(
        repository
    )

    @Provides
    @Singleton
    fun provideSaveTestRequestUseCase(
        repository: RequestTestDeviceRepository,
    ): SaveTestDeviceRequestUseCase = SaveTestDeviceRequestUseCase(
        repository
    )

    @Provides
    @Singleton
    fun provideSignInUseCases(
        repository: AuthenticationService,
        signInWithGoogle: SignInWithGoogleUseCase,
        hasUser: HasUserUseCase,
        signOut: SignOutUseCase
    ): SignInUseCases = SignInUseCases(repository, signInWithGoogle, hasUser, signOut)

    @Provides
    @Singleton
    fun provideSignInWithGoogleUseCase(
        repository: AuthenticationService,
    ): SignInWithGoogleUseCase = SignInWithGoogleUseCase(
        repository
    )

    @Provides
    @Singleton
    fun provideHasUserUseCase(
        repository: AuthenticationService,
    ): HasUserUseCase = HasUserUseCase(
        repository
    )

    @Provides
    @Singleton
    fun provideSignOutUseCase(
        repository: AuthenticationService,
    ): SignOutUseCase = SignOutUseCase(
        repository
    )

}

