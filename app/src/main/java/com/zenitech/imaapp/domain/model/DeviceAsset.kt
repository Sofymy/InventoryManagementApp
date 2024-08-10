package com.zenitech.imaapp.domain.model

import com.zenitech.imaapp.ui.model.DeviceAssetUi

enum class DeviceAsset {
    MONITOR,
    PREMIUM_ADAPTER,
    SPEAKERPHONE,
    TV,
    NETWORK_ATTACHED_STORAGE_NAS,
    PREMIUM_CATEGORY_ADAPTER,
    PRINTER,
    SWITCH,
    DESKTOP_COMPUTER,
    SOLID_STATE_DRIVE_SSD,
    HARD_DISK_DRIVE_HDD,
    ROUTER,
    DVR,
    SECURITY_CAMERA,
    WIFI_ACCESS_POINT_AP,
    TABLET,
    UPS,
    RACK_CABINET,
    SERVER,
    MINI_PC,
    SPEAKER,
    CONSOLE,
    MOBILE_WIFI,
    VIDEO_CONFERENCING_SYSTEM,
    DOCKING_STATION,
    MICROPHONE,
    TV_STAND,
    POWER_DISTRIBUTION_UNIT_PDU,
    LAPTOP,
    PHONE,
    PC,
    HEADPHONES,
    CARD_READER,
    PREMIUM_KEYBOARD,
    TRACKPAD,
    MEMORY,
    PREMIUM_MOUSE,
    HEADSET,
    POWER_SUPPLY,
    PREMIUM_WEBCAM,
    EXTERNAL_HDD,
    GRAPHICS_CARD,
    SMARTWATCH,
    FIREWIRE,
    DIGITIZER_TABLET,
    HOME_THEATER,
    INDUSTRIAL_PC,
    VR_GLASSES,
    EXTERNAL_DRIVE,
    PROJECTOR,
    BATTERY,
    CABLE,
    VIDEO_SPLITTER,
    PEN,
    STORAGE,
    SECURITY_KEY,
    CHROMECAST
}


fun DeviceAsset.asDeviceAssetUi(): DeviceAssetUi {
    return when (this) {
        DeviceAsset.MONITOR -> DeviceAssetUi.Monitor
        DeviceAsset.PREMIUM_ADAPTER -> DeviceAssetUi.PremiumAdapter
        DeviceAsset.SPEAKERPHONE -> DeviceAssetUi.Speakerphone
        DeviceAsset.TV -> DeviceAssetUi.Tv
        DeviceAsset.NETWORK_ATTACHED_STORAGE_NAS -> DeviceAssetUi.NetworkAttachedStorageNas
        DeviceAsset.PREMIUM_CATEGORY_ADAPTER -> DeviceAssetUi.PremiumCategoryAdapter
        DeviceAsset.PRINTER -> DeviceAssetUi.Printer
        DeviceAsset.SWITCH -> DeviceAssetUi.Switch
        DeviceAsset.DESKTOP_COMPUTER -> DeviceAssetUi.DesktopComputer
        DeviceAsset.SOLID_STATE_DRIVE_SSD -> DeviceAssetUi.SolidStateDriveSsd
        DeviceAsset.HARD_DISK_DRIVE_HDD -> DeviceAssetUi.HardDiskDriveHdd
        DeviceAsset.ROUTER -> DeviceAssetUi.Router
        DeviceAsset.DVR -> DeviceAssetUi.Dvr
        DeviceAsset.SECURITY_CAMERA -> DeviceAssetUi.SecurityCamera
        DeviceAsset.WIFI_ACCESS_POINT_AP -> DeviceAssetUi.WifiAccessPointAp
        DeviceAsset.TABLET -> DeviceAssetUi.Tablet
        DeviceAsset.UPS -> DeviceAssetUi.Ups
        DeviceAsset.RACK_CABINET -> DeviceAssetUi.RackCabinet
        DeviceAsset.SERVER -> DeviceAssetUi.Server
        DeviceAsset.MINI_PC -> DeviceAssetUi.MiniPc
        DeviceAsset.SPEAKER -> DeviceAssetUi.Speaker
        DeviceAsset.CONSOLE -> DeviceAssetUi.Console
        DeviceAsset.MOBILE_WIFI -> DeviceAssetUi.MobileWifi
        DeviceAsset.VIDEO_CONFERENCING_SYSTEM -> DeviceAssetUi.VideoConferencingSystem
        DeviceAsset.DOCKING_STATION -> DeviceAssetUi.DockingStation
        DeviceAsset.MICROPHONE -> DeviceAssetUi.Microphone
        DeviceAsset.TV_STAND -> DeviceAssetUi.TvStand
        DeviceAsset.POWER_DISTRIBUTION_UNIT_PDU -> DeviceAssetUi.PowerDistributionUnitPdu
        DeviceAsset.LAPTOP -> DeviceAssetUi.Laptop
        DeviceAsset.PHONE -> DeviceAssetUi.Phone
        DeviceAsset.PC -> DeviceAssetUi.Pc
        DeviceAsset.HEADPHONES -> DeviceAssetUi.Headphones
        DeviceAsset.CARD_READER -> DeviceAssetUi.CardReader
        DeviceAsset.PREMIUM_KEYBOARD -> DeviceAssetUi.PremiumKeyboard
        DeviceAsset.TRACKPAD -> DeviceAssetUi.Trackpad
        DeviceAsset.MEMORY -> DeviceAssetUi.Memory
        DeviceAsset.PREMIUM_MOUSE -> DeviceAssetUi.PremiumMouse
        DeviceAsset.HEADSET -> DeviceAssetUi.Headset
        DeviceAsset.POWER_SUPPLY -> DeviceAssetUi.PowerSupply
        DeviceAsset.PREMIUM_WEBCAM -> DeviceAssetUi.PremiumWebcam
        DeviceAsset.EXTERNAL_HDD -> DeviceAssetUi.ExternalHdd
        DeviceAsset.GRAPHICS_CARD -> DeviceAssetUi.GraphicsCard
        DeviceAsset.SMARTWATCH -> DeviceAssetUi.Smartwatch
        DeviceAsset.FIREWIRE -> DeviceAssetUi.Firewire
        DeviceAsset.DIGITIZER_TABLET -> DeviceAssetUi.DigitizerTablet
        DeviceAsset.HOME_THEATER -> DeviceAssetUi.HomeTheater
        DeviceAsset.INDUSTRIAL_PC -> DeviceAssetUi.IndustrialPc
        DeviceAsset.VR_GLASSES -> DeviceAssetUi.VrGlasses
        DeviceAsset.EXTERNAL_DRIVE -> DeviceAssetUi.ExternalDrive
        DeviceAsset.PROJECTOR -> DeviceAssetUi.Projector
        DeviceAsset.BATTERY -> DeviceAssetUi.Battery
        DeviceAsset.CABLE -> DeviceAssetUi.Cable
        DeviceAsset.VIDEO_SPLITTER -> DeviceAssetUi.VideoSplitter
        DeviceAsset.PEN -> DeviceAssetUi.Pen
        DeviceAsset.STORAGE -> DeviceAssetUi.Storage
        DeviceAsset.SECURITY_KEY -> DeviceAssetUi.SecurityKey
        DeviceAsset.CHROMECAST -> DeviceAssetUi.Chromecast
    }
}

fun DeviceAssetUi.asDeviceAsset(): DeviceAsset {
    return when (this) {
        DeviceAssetUi.Monitor -> DeviceAsset.MONITOR
        DeviceAssetUi.PremiumAdapter -> DeviceAsset.PREMIUM_ADAPTER
        DeviceAssetUi.Speakerphone -> DeviceAsset.SPEAKERPHONE
        DeviceAssetUi.Tv -> DeviceAsset.TV
        DeviceAssetUi.NetworkAttachedStorageNas -> DeviceAsset.NETWORK_ATTACHED_STORAGE_NAS
        DeviceAssetUi.PremiumCategoryAdapter -> DeviceAsset.PREMIUM_CATEGORY_ADAPTER
        DeviceAssetUi.Printer -> DeviceAsset.PRINTER
        DeviceAssetUi.Switch -> DeviceAsset.SWITCH
        DeviceAssetUi.DesktopComputer -> DeviceAsset.DESKTOP_COMPUTER
        DeviceAssetUi.SolidStateDriveSsd -> DeviceAsset.SOLID_STATE_DRIVE_SSD
        DeviceAssetUi.HardDiskDriveHdd -> DeviceAsset.HARD_DISK_DRIVE_HDD
        DeviceAssetUi.Router -> DeviceAsset.ROUTER
        DeviceAssetUi.Dvr -> DeviceAsset.DVR
        DeviceAssetUi.SecurityCamera -> DeviceAsset.SECURITY_CAMERA
        DeviceAssetUi.WifiAccessPointAp -> DeviceAsset.WIFI_ACCESS_POINT_AP
        DeviceAssetUi.Tablet -> DeviceAsset.TABLET
        DeviceAssetUi.Ups -> DeviceAsset.UPS
        DeviceAssetUi.RackCabinet -> DeviceAsset.RACK_CABINET
        DeviceAssetUi.Server -> DeviceAsset.SERVER
        DeviceAssetUi.MiniPc -> DeviceAsset.MINI_PC
        DeviceAssetUi.Speaker -> DeviceAsset.SPEAKER
        DeviceAssetUi.Console -> DeviceAsset.CONSOLE
        DeviceAssetUi.MobileWifi -> DeviceAsset.MOBILE_WIFI
        DeviceAssetUi.VideoConferencingSystem -> DeviceAsset.VIDEO_CONFERENCING_SYSTEM
        DeviceAssetUi.DockingStation -> DeviceAsset.DOCKING_STATION
        DeviceAssetUi.Microphone -> DeviceAsset.MICROPHONE
        DeviceAssetUi.TvStand -> DeviceAsset.TV_STAND
        DeviceAssetUi.PowerDistributionUnitPdu -> DeviceAsset.POWER_DISTRIBUTION_UNIT_PDU
        DeviceAssetUi.Laptop -> DeviceAsset.LAPTOP
        DeviceAssetUi.Phone -> DeviceAsset.PHONE
        DeviceAssetUi.Pc -> DeviceAsset.PC
        DeviceAssetUi.Headphones -> DeviceAsset.HEADPHONES
        DeviceAssetUi.CardReader -> DeviceAsset.CARD_READER
        DeviceAssetUi.PremiumKeyboard -> DeviceAsset.PREMIUM_KEYBOARD
        DeviceAssetUi.Trackpad -> DeviceAsset.TRACKPAD
        DeviceAssetUi.Memory -> DeviceAsset.MEMORY
        DeviceAssetUi.PremiumMouse -> DeviceAsset.PREMIUM_MOUSE
        DeviceAssetUi.Headset -> DeviceAsset.HEADSET
        DeviceAssetUi.PowerSupply -> DeviceAsset.POWER_SUPPLY
        DeviceAssetUi.PremiumWebcam -> DeviceAsset.PREMIUM_WEBCAM
        DeviceAssetUi.ExternalHdd -> DeviceAsset.EXTERNAL_HDD
        DeviceAssetUi.GraphicsCard -> DeviceAsset.GRAPHICS_CARD
        DeviceAssetUi.Smartwatch -> DeviceAsset.SMARTWATCH
        DeviceAssetUi.Firewire -> DeviceAsset.FIREWIRE
        DeviceAssetUi.DigitizerTablet -> DeviceAsset.DIGITIZER_TABLET
        DeviceAssetUi.HomeTheater -> DeviceAsset.HOME_THEATER
        DeviceAssetUi.IndustrialPc -> DeviceAsset.INDUSTRIAL_PC
        DeviceAssetUi.VrGlasses -> DeviceAsset.VR_GLASSES
        DeviceAssetUi.ExternalDrive -> DeviceAsset.EXTERNAL_DRIVE
        DeviceAssetUi.Projector -> DeviceAsset.PROJECTOR
        DeviceAssetUi.Battery -> DeviceAsset.BATTERY
        DeviceAssetUi.Cable -> DeviceAsset.CABLE
        DeviceAssetUi.VideoSplitter -> DeviceAsset.VIDEO_SPLITTER
        DeviceAssetUi.Pen -> DeviceAsset.PEN
        DeviceAssetUi.Storage -> DeviceAsset.STORAGE
        DeviceAssetUi.SecurityKey -> DeviceAsset.SECURITY_KEY
        DeviceAssetUi.Chromecast -> DeviceAsset.CHROMECAST
    }
}