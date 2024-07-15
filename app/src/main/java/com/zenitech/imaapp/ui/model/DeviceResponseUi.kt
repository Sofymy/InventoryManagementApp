package com.zenitech.imaapp.ui.model

import com.zenitech.imaapp.domain.model.DeviceResponse


data class DeviceResponseUi(
    val inventoryNumber: String,      // Leltáriszám* (Inventory Number)
    val assetName: DeviceTypeUi,            // Eszköz neve* (Asset name)
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


