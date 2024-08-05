package com.zenitech.imaapp.data.repository

import kotlinx.coroutines.flow.Flow

interface RequestTestDeviceRepository {
    fun getDeviceManufacturers(): Flow<List<String>>
    fun getDeviceTypes(): Flow<List<String>>
}