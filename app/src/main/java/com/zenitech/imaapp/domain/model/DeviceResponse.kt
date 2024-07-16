package com.zenitech.imaapp.domain.model

import com.zenitech.imaapp.ui.model.DeviceResponseUi
import com.zenitech.imaapp.ui.model.DeviceTypeUi
import com.zenitech.imaapp.ui.model.asDeviceType
import com.zenitech.imaapp.ui.model.asDeviceTypeUi

data class DeviceResponse(
    val inventoryNumber: String,      // Leltáriszám* (Inventory Number)
    val assetName: DeviceType,            // Eszköz neve* (Asset name)
    /*    val manufacturer: String,         // Gyártó* (Manufacturer)
        val type: DeviceTypeUi,                 // Típus* (Type)
        val serialNumber: String,         // Gyártási szám* (Manufacturing number)
        val shipmentDate: String,         // Szállítási idő* (Delivery time)
        val status: String,               // Státusz* (Status)
        val condition: String,            // Állapot* (Condition)
        val dateOfHandover: String,       // Kiadás ideje* (Date of handover)
        val userName: String,             // Használó neve* (User Name)
        val site: String,                 // Fellelhetőség* (Availability)
        val location: String,             // Fellelhetőség* (Availability)
        val note: String,                 // Megjegyzés (Note)
        val supplier: String,             // Beszállító* (Supplier)
        val leases: String?,              // Bérlés (Leases)
        val testDeviceLesseeName: String?,// Teszt eszköz bérlő neve (Test device lessee name)
        val startOfTestDeviceLease: String?, // Teszt eszköz bérlés kezdete (Start of test device lease)
        val endOfTestDeviceLease: String?,   // Teszte eszköz bérlés vége (End of test device lease)
        val tags: List<String>?,          // Tagek (Tags)
        val invoiceNumber: String?,       // Számlaszám (Invoice number)
        val warranty: String?,            // Garancia (Warranty)
        val repairStartDate: String?,     // Javítás kezdete (Repair start date)
        val repairEndDate: String?        // Javítás vége (Repair end date)*/
)

fun DeviceResponse.asDeviceResponseUi(): DeviceResponseUi = DeviceResponseUi(
    inventoryNumber = inventoryNumber,
    assetName = assetName.asDeviceTypeUi()
)

fun DeviceResponseUi.asDeviceResponse(): DeviceResponse = DeviceResponse(
    inventoryNumber = inventoryNumber,
    assetName = assetName.asDeviceType()
)