package com.zenitech.imaapp.data.repository

import com.zenitech.imaapp.domain.model.CreateDeviceRequest
import com.zenitech.imaapp.domain.model.DeviceAsset
import com.zenitech.imaapp.domain.model.DeviceCondition
import com.zenitech.imaapp.domain.model.DeviceResponse
import com.zenitech.imaapp.domain.model.DeviceSearchRequest
import com.zenitech.imaapp.domain.model.DeviceStatus
import com.zenitech.imaapp.domain.model.HistoryResponse
import com.zenitech.imaapp.domain.model.RequestStatus
import com.zenitech.imaapp.domain.model.TestDeviceRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

class AdminRepositoryImpl : AdminRepository {
    override fun getAdminDevices(): Flow<List<DeviceSearchRequest>> {
        return flow {
            val mockedDevices = listOf(
                    DeviceSearchRequest(
                        inventoryId = "INV001",
                        type = "Laptop",
                        manufacturer = "Dell",
                        serialNumber = "SN123456",
                        assetName = DeviceAsset.PHONE,
                        supplier = "TechSupply",
                        invoiceNumber = "INV0001",
                        shipmentDateBegin = "2023-01-01",
                        shipmentDateEnd = "2023-01-10",
                        warrantyBegin = "2023-01-11",
                        warrantyEnd = "2024-01-11",
                        condition = DeviceCondition.NEW,
                        status = DeviceStatus.DELETED,
                        leaseStartDate = "2023-02-01",
                        leaseEndDate = "2024-02-01",
                        userName = "John Doe",
                        site = "Headquarters",
                        location = "Office A",
                        note = "Urgent delivery",
                        tags = listOf("urgent", "high-value"),
                        isTestDevice = false
                    ),
                    DeviceSearchRequest(
                        inventoryId = "INV002",
                        type = "Monitor",
                        manufacturer = "Samsung",
                        serialNumber = "SN654321",
                        assetName = DeviceAsset.MONITOR,
                        supplier = "VisionTech",
                        invoiceNumber = "INV0002",
                        shipmentDateBegin = "2023-02-01",
                        shipmentDateEnd = "2023-02-15",
                        warrantyBegin = "2023-02-16",
                        warrantyEnd = "2024-02-16",
                        condition = DeviceCondition.USED,
                        status = DeviceStatus.MISSING,
                        leaseStartDate = "2023-03-01",
                        leaseEndDate = "2024-03-01",
                        userName = "Jane Smith",
                        site = "Branch Office",
                        location = "Office B",
                        note = null,
                        tags = listOf("monitor", "refurbished"),
                        isTestDevice = true
                    ),
                    DeviceSearchRequest(
                        inventoryId = "INV003",
                        type = "Printer",
                        manufacturer = "HP",
                        serialNumber = "SN789012",
                        assetName = DeviceAsset.PRINTER,
                        supplier = "PrintMasters",
                        invoiceNumber = "INV0003",
                        shipmentDateBegin = "2023-03-01",
                        shipmentDateEnd = "2023-03-10",
                        warrantyBegin = "2023-03-11",
                        warrantyEnd = "2024-03-11",
                        condition = DeviceCondition.USED,
                        status = DeviceStatus.ON_REPAIR,
                        leaseStartDate = "2023-04-01",
                        leaseEndDate = "2024-04-01",
                        userName = "Emily Johnson",
                        site = "Headquarters",
                        location = "Office C",
                        note = "Needs calibration",
                        tags = listOf("printer", "needs-calibration"),
                        isTestDevice = false
                    ),
                    DeviceSearchRequest(
                        inventoryId = "INV004",
                        type = "Router",
                        manufacturer = "Cisco",
                        serialNumber = "SN345678",
                        assetName = DeviceAsset.ROUTER,
                        supplier = "NetEquip",
                        invoiceNumber = "INV0004",
                        shipmentDateBegin = "2023-04-01",
                        shipmentDateEnd = "2023-04-05",
                        warrantyBegin = "2023-04-06",
                        warrantyEnd = "2024-04-06",
                        condition = DeviceCondition.NEW,
                        status = DeviceStatus.ARCHIVED,
                        leaseStartDate = "2023-05-01",
                        leaseEndDate = "2024-05-01",
                        userName = "Michael Brown",
                        site = "Data Center",
                        location = "Rack 5",
                        note = "Critical infrastructure",
                        tags = listOf("network", "critical"),
                        isTestDevice = false
                    ),
                    DeviceSearchRequest(
                        inventoryId = "INV005",
                        type = "Server",
                        manufacturer = "HP",
                        serialNumber = "SN987654",
                        assetName = DeviceAsset.SERVER,
                        supplier = "ServerWorld",
                        invoiceNumber = "INV0005",
                        shipmentDateBegin = "2023-05-01",
                        shipmentDateEnd = "2023-05-15",
                        warrantyBegin = "2023-05-16",
                        warrantyEnd = "2024-05-16",
                        condition = DeviceCondition.NEW,
                        status = DeviceStatus.IN_STORAGE,
                        leaseStartDate = "2023-06-01",
                        leaseEndDate = "2024-06-01",
                        userName = "Sarah Wilson",
                        site = "Headquarters",
                        location = "Server Room 1",
                        note = "High performance",
                        tags = listOf("server", "high-performance"),
                        isTestDevice = true
                    ),
                    DeviceSearchRequest(
                        inventoryId = "INV006",
                        type = "Tablet",
                        manufacturer = "Apple",
                        serialNumber = "SN135791",
                        assetName = DeviceAsset.TABLET,
                        supplier = "GadgetStore",
                        invoiceNumber = "INV0006",
                        shipmentDateBegin = "2023-06-01",
                        shipmentDateEnd = "2023-06-10",
                        warrantyBegin = "2023-06-11",
                        warrantyEnd = "2024-06-11",
                        condition = DeviceCondition.NEW,
                        status = DeviceStatus.DRAFT,
                        leaseStartDate = "2023-07-01",
                        leaseEndDate = "2024-07-01",
                        userName = "Lucas Martin",
                        site = "Branch Office",
                        location = "Office D",
                        note = "For testing",
                        tags = listOf("tablet", "testing"),
                        isTestDevice = false
                    ),
                    DeviceSearchRequest(
                        inventoryId = "INV007",
                        type = "Phone",
                        manufacturer = "Google",
                        serialNumber = "SN246810",
                        assetName = DeviceAsset.PHONE,
                        supplier = "MobileStore",
                        invoiceNumber = "INV0007",
                        shipmentDateBegin = "2023-07-01",
                        shipmentDateEnd = "2023-07-05",
                        warrantyBegin = "2023-07-06",
                        warrantyEnd = "2024-07-06",
                        condition = DeviceCondition.DAMAGED,
                        status = DeviceStatus.MISSING,
                        leaseStartDate = "2023-08-01",
                        leaseEndDate = "2024-08-01",
                        userName = "Olivia Brown",
                        site = "Headquarters",
                        location = "Office E",
                        note = "New model",
                        tags = listOf("phone", "new-model"),
                        isTestDevice = false
                    ),
                    DeviceSearchRequest(
                        inventoryId = "INV008",
                        type = "Camera",
                        manufacturer = "Canon",
                        serialNumber = "SN112233",
                        assetName = DeviceAsset.SECURITY_CAMERA,
                        supplier = "CameraShop",
                        invoiceNumber = "INV0008",
                        shipmentDateBegin = "2023-08-01",
                        shipmentDateEnd = "2023-08-10",
                        warrantyBegin = "2023-08-11",
                        warrantyEnd = "2024-08-11",
                        condition = DeviceCondition.USED,
                        status = DeviceStatus.DISCARDED,
                        leaseStartDate = "2023-09-01",
                        leaseEndDate = "2024-09-01",
                        userName = "Daniel Lee",
                        site = "Headquarters",
                        location = "Office F",
                        note = "For events",
                        tags = listOf("camera", "events"),
                        isTestDevice = true
                    ),
                    DeviceSearchRequest(
                        inventoryId = "INV009",
                        type = "Speaker",
                        manufacturer = "Bose",
                        serialNumber = "SN334455",
                        assetName = DeviceAsset.SPEAKER,
                        supplier = "AudioHub",
                        invoiceNumber = "INV0009",
                        shipmentDateBegin = "2023-09-01",
                        shipmentDateEnd = "2023-09-05",
                        warrantyBegin = "2023-09-06",
                        warrantyEnd = "2024-09-06",
                        condition = DeviceCondition.NEW,
                        status = DeviceStatus.LEASED,
                        leaseStartDate = "2023-10-01",
                        leaseEndDate = "2024-10-01",
                        userName = "Sophia Wilson",
                        site = "Branch Office",
                        location = "Office G",
                        note = null,
                        tags = listOf("speaker", "new"),
                        isTestDevice = false
                    ),
                    DeviceSearchRequest(
                        inventoryId = "INV010",
                        type = "Smartwatch",
                        manufacturer = "Fitbit",
                        serialNumber = "SN556677",
                        assetName = DeviceAsset.SMARTWATCH,
                        supplier = "WearableTech",
                        invoiceNumber = "INV0010",
                        shipmentDateBegin = "2023-10-01",
                        shipmentDateEnd = "2023-10-10",
                        warrantyBegin = "2023-10-11",
                        warrantyEnd = "2024-10-11",
                        condition = DeviceCondition.NEW,
                        status = DeviceStatus.LEASED,
                        leaseStartDate = "2023-11-01",
                        leaseEndDate = "2024-11-01",
                        userName = "Ava Johnson",
                        site = "Headquarters",
                        location = "Office H",
                        note = "Fitness tracker",
                        tags = listOf("smartwatch", "fitness"),
                        isTestDevice = true
                    )
            )
            val orderedMockedDevices = mockedDevices.sortedBy { it.inventoryId }
            emit(orderedMockedDevices)
        }
    }
    override fun getDeviceTypes(): Flow<List<String>> {
        return flow {
            val mockedDeviceAssets = mutableListOf(
                "Acer T232HL",
                "Acer T232HL",
                "Acer X168H, DLP 3D Projector",
                "Advantech PPC-3150-RE4AE",
                "Alcatel 1T 10\" 16GB Fekete",
                "Alcatel Link Zone MW40V",
                "APC SmartConnect SMC1500I-2UC",
                "APC BX 700UI",
                "APC Back-UPS BX1400U-GR 1400VA",
                "APC AP8959 Rack PDU 2G, Kapcsolt, Zero-U, 20 A/208 V, 16 A/230 V, (21) C13 & (3) C19",
                "APC Easy UPS BV 1000VA, AVR Schuko Outlet, 230V, Line-Interactive Szünetmentes Tápegység",
                "Apple Lightning to Digital AV Adapter (HDMI)",
                "Apple Lightning to VGA Adapter",
                "AppleTV 4",
                "Mac Mini",
                "Mac Mini SG 3.2GHz 6 Core i7 16GB DDR4 256GB SSD Intel UHD Graphics 630",
                "AppleTV 4K (32GB)-MGP",
                "Apple Mac Mini 2018 MRTT2i5 3GHz, 8GB, 256GB, UHDG630",
                "Apple MacBook Pro m3 36GB RAM",
                "MacBook Pro 13\" CTO Retina DC i5, 2.3 GHz, 16 GB RAM, 512 GB SSD, Asztroszürke, Nemzetközi Angol Billentyűzet",
                "Billentyűzet Apple Magic Keyboard Num - US",
                "Apple Magic Trackpad 3 (2021)",
                "Apple MacBook Pro m3 48GB RAM",
                "MacBook Air 13.6-Inch + 8GB RAM+256 SSD",
                "Apple Vezetékes Billentyűzet US",
                "Apple MacBook Pro m3 36GB RAM",
                "Apple MacBook Pro 13\" Touch Bar/QC i5 2.3GHz/8GB/256GB SSD/Intel Iris Plus Graphics 655/Space Grey - HUN KB (2018)",
                "ZTO MacBook Pro 13\" 8C M1/16GB/512GB SSD/HUN.KEYB/Space Grey",
                "13-Inch MacBook Pro: Apple M1 8 Core CPU, 8 Core GPU, 256GB SSD - Space Grey",
                "Apple AirPods Pro",
                "Apple USB-C 20W Power Adapter",
                "MacBook Pro 16.2 Space Grey/M1 Max/32C GPU/32GB/1T-MAG",
                "Apple MacBook Pro 14\" Notebook, Asztroszürke 8C M1/14C GPU/16GB/512G",
                "MacBook Pro 16",
                "Apple iPhone X Space Gray 64GB-HBG",
                "iPhone 12 Pro Max 128GB Pacific Blue",
                "Apple - Thunderbolt 3 (USB C) – Thunderbolt 2 Adapter",
                "Apple - Thunderbolt - Gigabit Ethernet Átalakító",
                "Apple - USB-C – Digitális AV Többportos Átalakító (Új)",
                "Apple Magic Keyboard (2021) with Touch ID and Numeric Keypad - Hungarian",
                "Apple Magic Mouse 3 (2021)",
                "Apple MacBook Pro m3 36GB RAM 512 GB SSD",
                "Apple MacBook Pro 14\" Notebook, Silver 8C M1/14C GPU/16GB/512G",
                "Apple iPhone 8 Plus 64GB Silver",
                "MacBook Pro 13\"",
                "MacBook Pro m2 32GB RAM",
                "MacBook Pro 13\" CTO Retina DC i5, 2.3 GHz, 16 GB RAM, 512 GB SSD, Ezüst, Nemzetközi Angol Billentyűzet",
                "Apple 12.9-Inch iPad Pro (5th) Wi-Fi 128GB - Space Grey",
                "Apple Magic Keyboard - Nemzetközi Angol",
                "Apple Magic Keyboard - Magyar MRMH2MG/A",
                "Apple Pro Display XDR Álvánnyal",
                "Apple iPhone 7 Black",
                "Apple Watch 3 Space Gray 42mm",
                "Apple Keyboard MB110MG/B",
                "Apple MB110MG/B Magyar",
                "Apple Vezetékes Billentyűzet HUN",
                "MacBook Pro 13\"",
                "Apple iPad Pro",
                "Apple iPhone 7 Rose Gold",
                "Apple A1243 Keyboard MB110MG/B",
                "Apple (Vezetékes)",
                "Apple iPhone XR 64 GB Black",
                "MacBook Pro 13.3\" CTO Retina, DCi5 2.3GHz, 256GB SSD, 16GB RAM",
                "Apple MacBook Pro CTO 15\" Touch Bar/QC i7 2.2GHz/16GB/512GB SSD/Radeon Pro 555X 4GB/Space Grey-INT",
                "Apple MacBook Pro CTO 15\" Touch Bar/QC i7 2.6GHz/16GB/512GB SSD/Radeon Pro 555X 4GB/Space Grey-INT",
                "Apple MacBook Pro CTO 15\" Touch Bar/QC i7 2.6GHz/16GB/512GB SSD/Radeon Pro 555X 4GB/Space Grey-INT KB (2019)",
                "MacBook Air 13.3\" Intel i5 1.6GHz, 8GB, 256GB SSD, Retina, Intel UHD G 615, Magyar, Asztroszürke",
                "MacBook Pro 16 2.6 GHz Intel Core i7 Processzor, Intel UHD Graphics 630, 16 GB DDR4",
                "Mac Mini Apple M1 8 Core, 8 Core GPU, 256GB SSD",
                "iPhone 12 Mini 64GB Black",
                "iPhone 12 Pro 128GB Graphite",
                "iPhone 12 Pro 128GB Pacific Blue",
                "Mac Pro 2.5GHz Intel Xeon W 28CPU/768GB DDR4/2 x W6800X W 32GB/8TB/HUN",
                "Apple Mac Mini/8C CPU/8C GPU 16GB/2TB/10GB ETH - MAG",
                "Apple 12.9-Inch iPad Pro (5th) Wi-Fi 128GB Space Grey",
                "Apple Magic Keyboard for iPad Pro 12.9-Inch (5th)- Hungarian - Black",
                "Apple Pencil (2nd Generation)",
                "Apple iPhone 13 Pro Max 128GB Graphite",
                "Iphone 15 Plus 256 GB Rózsaszín",
                "MacBook Pro 14\" 16GB RAM+512 SSD",
                "Apple iPad Pro 12.9\"",
                "Apple MacBook Pro m3 36GB RAM 512 GB SSD Black",
                "MacBook Pro 14\" CTO Z15 G Asztroszürke",
                "MacBook Pro 13\" 2016",
                "iPad Pro 12.9 256 GB",
                "ZTO MacBook Pro 13\" 8C M1/16GB/1TB SSD/HUN.KEYB/Space Grey",
                "Aruba 205",
                "Asus ROG Strix X570-F AMD Ryzen 9 3950X 16-Core 3.5GHz 128GB DDR4 3200MHz 4TB SSD",
                "Asus X571GT-AL299C",
                "Mini PC Asus NUC 13 Pro NUC13ANHI5",
                "Asus Z170-K – Alaplap, Intel i7 - 3.4GHz – Processzor, Crucial 16GB – RAM, Samsung 250GB – SSD, Toshiba 1TB – HDD, Chieftech GPM 550 S – Táp, Coolermaster Silencio 550 - Ház",
                "Asus N551JW-CN067D",
                "ZenBook Pro 14 Duo",
                "Asus UX550VE-BN038T 15.6\" FHD, i7-7700HQ 16GB, 512GB PCIe SSD, GTX 1050Ti 4GB, Win10 Home, Fekete",
                "Asus ZenBook UX530UX-FY048T 15.6\" FHD, i7-7500U, 16GB, 512GB SSD, Nvidia GF 950MX, Win10, Ezüst",
                "Asus ZenBook 13 UX331UA-EG077T",
                "Asus ZenBook 14 UX433FN - i7-8565U, 14\" FHD, 512 GB, 16GB, GeForce MX150 2GB, Win10",
                "Asus Prime Z690-P D4 Intel Core i7-12700K, 64GB DDR4, 240B+2x1GB SSD",
                "AsRock Z270 Gaming K4 Alaplap, Intel i7 6700 Processzor, Crucial 16GB Memória, Samsung 250 GB SSD, Toshiba 1 TB HDD, Chieftech GPM 550 S Táp, Coolermaster Silencio 550 Ház",
                "Asus ROG Strix X570-F, AMD Ryzen 9 5950X 128GB DDR4 RAM, 4TB SSD, 8TB HDD",
                "BenQ"
                )
            val orderedMockedDeviceAssets = mockedDeviceAssets.sorted()
            emit(orderedMockedDeviceAssets)
        }
    }

    override fun getDeviceAssets(): Flow<List<String>> {
        return flow {
            val mockedDeviceTypes = mutableListOf(
                "Monitor",
                "Premium adapter",
                "Speakerphone",
                "Tv",
                "Network attached storage nas",
                "Premium category adapter",
                "Printer",
                "Switch",
                "Desktop computer",
                "Solid state drive ssd",
                "Hard disk drive hdd",
                "Router",
                "Dvr",
                "Security camera",
                "Wifi access point ap",
                "Tablet",
                "Ups",
                "Rack cabinet",
                "Server",
                "Mini pc",
                "Speaker",
                "Console",
                "Mobile wifi",
                "Video conferencing system",
                "Docking station",
                "Microphone",
                "Tv stand",
                "Power distribution unit pdu",
                "Laptop",
                "Phone",
                "Pc",
                "Headphones",
                "Card reader",
                "Premium keyboard",
                "Trackpad",
                "Memory",
                "Premium mouse",
                "Headset",
                "Power supply",
                "Premium webcam",
                "External hdd",
                "Graphics card",
                "Smartwatch",
                "Firewire",
                "Digitizer tablet",
                "Home theater",
                "Industrial pc",
                "Vr glasses",
                "External drive",
                "Projector",
                "Battery",
                "Cable",
                "Video splitter",
                "Pen",
                "Storage",
                "Security key",
                "Chromecast"
            )
            val orderedMockedDeviceTypes = mockedDeviceTypes.sorted()
            emit(orderedMockedDeviceTypes)
        }
    }

    override fun getDeviceSites(): Flow<List<String>> {
        return flow {
            val mockedlocations = listOf(
                "Ismeretlen",
                "C ép. E bejárat",
                "A ép. II. em",
                "átadott",
                "BME",
                "Budapest",
                "C Atlantis",
                "C Discovery",
                "C Enterprise",
                "C ép. A bejárat",
                "C ép. AB",
                "C ép. AB konyha",
                "C ép. AB szerverszoba",
                "C ép. B bejárat",
                "C ép. C bejárat",
                "C ép. CD",
                "C ép. CD szerverszoba",
                "C ép. D bejárat",
                "C ÉP. EF",
                "C ép. EF Szerver bejárat",
                "C Ép. EF szerverszoba",
                "C ép. F bejárat",
                "C ép. Szerver szoba",
                "C Falcon",
                "C Recepció",
                "C Szerver",
                "C Szerver szoba ( Raktár )",
                "C Szerverszoba",
                "C Voyager1",
                "C Voyager1 (raktár)",
                "C Voyager2 (raktár)",
                "C06",
                "C07",
                "C08",
                "C10",
                "C11",
                "C12",
                "C13",
                "C17",
                "C18",
                "C19",
                "C20",
                "C24",
                "C24/2",
                "C31",
                "C34",
                "C35",
                "C36",
                "C37",
                "C38",
                "C39",
                "C40",
                "C41",
                "C42",
                "C43",
                "C49",
                "C50",
                "C6",
                "C7",
                "C8",
                "C9",
                "CD folyosó",
                "General Automotive",
                "HO",
                "Miskolc",
                "Nincs meg",
                "QB116",
                "QB120",
                "QB218",
                "QB221",
                "QB223",
                "QB229",
                "QB233",
                "QB234",
                "QB236",
                "RackForest",
                "Raktár",
                "Servergarden",
                "Szerver szoba ( Raktár )",
                "Szerver szoba (raktár)",
                "Szerverszoba",
                "Szerverterem",
                "Voyager 2",
                "Voyager Raktár",
                "C Masat",
                "C Tardis"
            )
            val orderedMockedLocations = mockedlocations.sorted()
            emit(orderedMockedLocations)
        }
    }

    override fun getRequests(): Flow<List<TestDeviceRequest>> {
        return flow {
            val mockedRequests = listOf(
                TestDeviceRequest(
                    requestId = "1a2b3c4d",
                    manufacturer = "Company A",
                    asset = "Device 1",
                    startDate = "2024-08-01",
                    endDate = "2024-08-07",
                    note = "First test device request",
                    status = RequestStatus.PENDING
                ),
                TestDeviceRequest(
                    requestId = "2b3c4d5e",
                    manufacturer = "Company B",
                    asset = "Device 2",
                    startDate = "2024-08-08",
                    endDate = "2024-08-14",
                    note = "Second test device request",
                    status = RequestStatus.PENDING
                ),
                TestDeviceRequest(
                    requestId = "2b3c4d5e",
                    manufacturer = "Company F",
                    asset = "Device 5",
                    startDate = "2024-08-01",
                    endDate = "2024-08-02",
                    note = "Fourth test device request",
                    status = RequestStatus.PENDING
                ),
                TestDeviceRequest(
                    requestId = "3c4d5e6f",
                    manufacturer = "Company C",
                    asset = "Device 3",
                    startDate = "2024-08-15",
                    endDate = "2024-08-21",
                    note = "Third test device request",
                    status = RequestStatus.REJECTED
                )
            )
            emit(mockedRequests)
        }
    }

    override fun createDevice(createDeviceRequest: CreateDeviceRequest): Flow<DeviceResponse> {
        return flow{
            val mockedDeviceResponse = DeviceResponse(
                inventoryId = "inv123",
                asset = DeviceAsset.ROUTER,
                manufacturer = "Sample Manufacturer",
                type = "Sample Type",
                serialNumber = "SN123456",
                shipmentDate = Date(1234567890L),
                status = DeviceStatus.LEASED,
                condition = DeviceCondition.USED,
                dateOfHandover = Date(1234567890L),
                userName = "John Doe",
                site = "Sample Site",
                location = "Sample Location",
                note = "This is a sample note for the device.",
                supplier = "Sample Supplier",
                invoiceNumber = "INV123456",
                warranty = Date(1234567890L)
            )
            emit(mockedDeviceResponse)
        }
    }

    override fun getDeviceHistory(inventoryId: String): Flow<List<HistoryResponse>> {
        return flow{
            val mockedDeviceHistory = listOf(
                HistoryResponse(
                    timestamp = "2024-08-26",
                    action = "Lease",
                    modifierEmail = "admin1@zenitech.co.uk",
                    description = "Leased device to user 1.",
                    actorEmail = "user1@zenitech.co.uk",
                ),
                HistoryResponse(
                    timestamp = "2024-08-25",
                    action = "Modify",
                    modifierEmail = "admin2@zenitech.co.uk",
                    description = "Modified device condition.",
                    actorEmail = "user1@zenitech.co.uk",
                ),
                HistoryResponse(
                    timestamp = "2024-08-24",
                    action = "Update",
                    modifierEmail = "admin1@zenitech.co.uk",
                    description = "",
                    actorEmail = null
                ))
            emit(mockedDeviceHistory)
        }
    }

    override fun assignDevice(assignDeviceRequest: String) {

    }

    override fun rejectRequest(assignDeviceRequest: String) {

    }

    override fun deleteDevice(device: DeviceSearchRequest) {

    }

    override fun saveModifications(device: DeviceSearchRequest) {

    }

    override fun getDeviceManufacturers(): Flow<List<String>> {
        return flow {
            val mockedDeviceManufacturers = mutableListOf(
                "Apple",
                "Samsung",
                "Huawei",
                "Xiaomi",
                "Oppo",
                "Vivo",
                "Sony",
                "LG",
                "Lenovo",
                "ASUS",
                "Microsoft",
                "Google",
                "Nokia",
                "OnePlus",
                "Motorola",
                "Dell",
                "HP",
                "Acer",
                "Amazon",
                "Panasonic"
            )
            emit(mockedDeviceManufacturers)
        }
    }
}
