package com.zenitech.imaapp.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.Dvr
import androidx.compose.material.icons.twotone.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.zenitech.imaapp.data.model.DeviceResponse
import com.zenitech.imaapp.domain.model.DeviceType

enum class DeviceTypeUi(
    val icon: ImageVector = Icons.TwoTone.Devices,
    val label: String = ""
) {
    Monitor(Icons.TwoTone.Monitor, "Monitor"),
    PremiumAdapter(Icons.TwoTone.DeviceHub, "Premium adapter"),
    Speakerphone(Icons.TwoTone.SpeakerPhone, "Speakerphone"),
    Tv(Icons.TwoTone.Tv, "Tv"),
    NetworkAttachedStorageNas(Icons.TwoTone.Storage, "Network attached storage nas"),
    PremiumCategoryAdapter(Icons.TwoTone.DeviceHub, "Premium category adapter"),
    Printer(Icons.TwoTone.Print, "Printer"),
    Switch(Icons.TwoTone.SwitchLeft, "Switch"),
    DesktopComputer(Icons.TwoTone.DesktopWindows, "Desktop computer"),
    SolidStateDriveSsd(Icons.TwoTone.Storage, "Solid state drive ssd"),
    HardDiskDriveHdd(Icons.TwoTone.Storage, "Hard disk drive hdd"),
    Router(Icons.TwoTone.Router, "Router"),
    Dvr(Icons.AutoMirrored.TwoTone.Dvr, "Dvr"),
    SecurityCamera(Icons.TwoTone.CameraAlt, "Security camera"),
    WifiAccessPointAp(Icons.TwoTone.CellWifi, "Wifi access point ap"),
    Tablet(Icons.TwoTone.TabletAndroid, "Tablet"),
    Ups(Icons.TwoTone.BatteryFull, "Ups"),
    RackCabinet(Icons.TwoTone.ViewModule, "Rack cabinet"),
    Server(Icons.TwoTone.Storage, "Server"),
    MiniPc(Icons.TwoTone.DesktopWindows, "Mini pc"),
    Speaker(Icons.TwoTone.Speaker, "Speaker"),
    Console(Icons.TwoTone.VideogameAsset, "Console"),
    MobileWifi(Icons.TwoTone.WifiCalling, "Mobile wifi"),
    VideoConferencingSystem(Icons.TwoTone.VideoSettings, "Video conferencing system"),
    DockingStation(Icons.TwoTone.Dock, "Docking station"),
    Microphone(Icons.TwoTone.Mic, "Microphone"),
    TvStand(Icons.TwoTone.ViewModule, "Tv stand"),
    PowerDistributionUnitPdu(Icons.TwoTone.Power, "Power distribution unit pdu"),
    Laptop(Icons.TwoTone.Laptop, "Laptop"),
    Phone(Icons.TwoTone.Smartphone, "Phone"),
    Pc(Icons.TwoTone.DesktopWindows, "Pc"),
    Headphones(Icons.TwoTone.Headset, "Headphones"),
    CardReader(Icons.TwoTone.CreditCard, "Card reader"),
    PremiumKeyboard(Icons.TwoTone.Keyboard, "Premium keyboard"),
    Trackpad(Icons.TwoTone.TouchApp, "Trackpad"),
    Memory(Icons.TwoTone.Memory, "Memory"),
    PremiumMouse(Icons.TwoTone.Mouse, "Premium mouse"),
    Headset(Icons.TwoTone.HeadsetMic, "Headset"),
    PowerSupply(Icons.TwoTone.Power, "Power supply"),
    PremiumWebcam(Icons.TwoTone.Videocam, "Premium webcam"),
    ExternalHdd(Icons.TwoTone.Storage, "External hdd"),
    GraphicsCard(Icons.TwoTone.GraphicEq, "Graphics card"),
    Smartwatch(Icons.TwoTone.Watch, "Smartwatch"),
    Firewire(Icons.TwoTone.Cable, "Firewire"),
    DigitizerTablet(Icons.TwoTone.TabletMac, "Digitizer tablet"),
    HomeTheater(Icons.TwoTone.Theaters, "Home theater"),
    IndustrialPc(Icons.TwoTone.DesktopWindows, "Industrial pc"),
    VrGlasses(Icons.TwoTone.Vrpano, "Vr glasses"),
    ExternalDrive(Icons.TwoTone.Storage, "External drive"),
    Projector(Icons.TwoTone.Tv, "Projector"),
    Battery(Icons.TwoTone.BatteryFull, "Battery"),
    Cable(Icons.TwoTone.Cable, "Cable"),
    VideoSplitter(Icons.TwoTone.Cable, "Video splitter"),
    Pen(Icons.TwoTone.Edit, "Pen"),
    Storage(Icons.TwoTone.Storage, "Storage"),
    SecurityKey(Icons.TwoTone.Security, "Security key"),
    Chromecast(Icons.TwoTone.Cast, "Chromecast")
}

fun DeviceType.asDeviceTypeUi(): DeviceTypeUi {
    return when (this) {
        DeviceType.MONITOR -> DeviceTypeUi.Monitor
        DeviceType.PREMIUM_ADAPTER -> DeviceTypeUi.PremiumAdapter
        DeviceType.SPEAKERPHONE -> DeviceTypeUi.Speakerphone
        DeviceType.TV -> DeviceTypeUi.Tv
        DeviceType.NETWORK_ATTACHED_STORAGE_NAS -> DeviceTypeUi.NetworkAttachedStorageNas
        DeviceType.PREMIUM_CATEGORY_ADAPTER -> DeviceTypeUi.PremiumCategoryAdapter
        DeviceType.PRINTER -> DeviceTypeUi.Printer
        DeviceType.SWITCH -> DeviceTypeUi.Switch
        DeviceType.DESKTOP_COMPUTER -> DeviceTypeUi.DesktopComputer
        DeviceType.SOLID_STATE_DRIVE_SSD -> DeviceTypeUi.SolidStateDriveSsd
        DeviceType.HARD_DISK_DRIVE_HDD -> DeviceTypeUi.HardDiskDriveHdd
        DeviceType.ROUTER -> DeviceTypeUi.Router
        DeviceType.DVR -> DeviceTypeUi.Dvr
        DeviceType.SECURITY_CAMERA -> DeviceTypeUi.SecurityCamera
        DeviceType.WIFI_ACCESS_POINT_AP -> DeviceTypeUi.WifiAccessPointAp
        DeviceType.TABLET -> DeviceTypeUi.Tablet
        DeviceType.UPS -> DeviceTypeUi.Ups
        DeviceType.RACK_CABINET -> DeviceTypeUi.RackCabinet
        DeviceType.SERVER -> DeviceTypeUi.Server
        DeviceType.MINI_PC -> DeviceTypeUi.MiniPc
        DeviceType.SPEAKER -> DeviceTypeUi.Speaker
        DeviceType.CONSOLE -> DeviceTypeUi.Console
        DeviceType.MOBILE_WIFI -> DeviceTypeUi.MobileWifi
        DeviceType.VIDEO_CONFERENCING_SYSTEM -> DeviceTypeUi.VideoConferencingSystem
        DeviceType.DOCKING_STATION -> DeviceTypeUi.DockingStation
        DeviceType.MICROPHONE -> DeviceTypeUi.Microphone
        DeviceType.TV_STAND -> DeviceTypeUi.TvStand
        DeviceType.POWER_DISTRIBUTION_UNIT_PDU -> DeviceTypeUi.PowerDistributionUnitPdu
        DeviceType.LAPTOP -> DeviceTypeUi.Laptop
        DeviceType.PHONE -> DeviceTypeUi.Phone
        DeviceType.PC -> DeviceTypeUi.Pc
        DeviceType.HEADPHONES -> DeviceTypeUi.Headphones
        DeviceType.CARD_READER -> DeviceTypeUi.CardReader
        DeviceType.PREMIUM_KEYBOARD -> DeviceTypeUi.PremiumKeyboard
        DeviceType.TRACKPAD -> DeviceTypeUi.Trackpad
        DeviceType.MEMORY -> DeviceTypeUi.Memory
        DeviceType.PREMIUM_MOUSE -> DeviceTypeUi.PremiumMouse
        DeviceType.HEADSET -> DeviceTypeUi.Headset
        DeviceType.POWER_SUPPLY -> DeviceTypeUi.PowerSupply
        DeviceType.PREMIUM_WEBCAM -> DeviceTypeUi.PremiumWebcam
        DeviceType.EXTERNAL_HDD -> DeviceTypeUi.ExternalHdd
        DeviceType.GRAPHICS_CARD -> DeviceTypeUi.GraphicsCard
        DeviceType.SMARTWATCH -> DeviceTypeUi.Smartwatch
        DeviceType.FIREWIRE -> DeviceTypeUi.Firewire
        DeviceType.DIGITIZER_TABLET -> DeviceTypeUi.DigitizerTablet
        DeviceType.HOME_THEATER -> DeviceTypeUi.HomeTheater
        DeviceType.INDUSTRIAL_PC -> DeviceTypeUi.IndustrialPc
        DeviceType.VR_GLASSES -> DeviceTypeUi.VrGlasses
        DeviceType.EXTERNAL_DRIVE -> DeviceTypeUi.ExternalDrive
        DeviceType.PROJECTOR -> DeviceTypeUi.Projector
        DeviceType.BATTERY -> DeviceTypeUi.Battery
        DeviceType.CABLE -> DeviceTypeUi.Cable
        DeviceType.VIDEO_SPLITTER -> DeviceTypeUi.VideoSplitter
        DeviceType.PEN -> DeviceTypeUi.Pen
        DeviceType.STORAGE -> DeviceTypeUi.Storage
        DeviceType.SECURITY_KEY -> DeviceTypeUi.SecurityKey
        DeviceType.CHROMECAST -> DeviceTypeUi.Chromecast
    }
}

fun DeviceTypeUi.asDeviceType(): DeviceType {
    return when (this) {
        DeviceTypeUi.Monitor -> DeviceType.MONITOR
        DeviceTypeUi.PremiumAdapter -> DeviceType.PREMIUM_ADAPTER
        DeviceTypeUi.Speakerphone -> DeviceType.SPEAKERPHONE
        DeviceTypeUi.Tv -> DeviceType.TV
        DeviceTypeUi.NetworkAttachedStorageNas -> DeviceType.NETWORK_ATTACHED_STORAGE_NAS
        DeviceTypeUi.PremiumCategoryAdapter -> DeviceType.PREMIUM_CATEGORY_ADAPTER
        DeviceTypeUi.Printer -> DeviceType.PRINTER
        DeviceTypeUi.Switch -> DeviceType.SWITCH
        DeviceTypeUi.DesktopComputer -> DeviceType.DESKTOP_COMPUTER
        DeviceTypeUi.SolidStateDriveSsd -> DeviceType.SOLID_STATE_DRIVE_SSD
        DeviceTypeUi.HardDiskDriveHdd -> DeviceType.HARD_DISK_DRIVE_HDD
        DeviceTypeUi.Router -> DeviceType.ROUTER
        DeviceTypeUi.Dvr -> DeviceType.DVR
        DeviceTypeUi.SecurityCamera -> DeviceType.SECURITY_CAMERA
        DeviceTypeUi.WifiAccessPointAp -> DeviceType.WIFI_ACCESS_POINT_AP
        DeviceTypeUi.Tablet -> DeviceType.TABLET
        DeviceTypeUi.Ups -> DeviceType.UPS
        DeviceTypeUi.RackCabinet -> DeviceType.RACK_CABINET
        DeviceTypeUi.Server -> DeviceType.SERVER
        DeviceTypeUi.MiniPc -> DeviceType.MINI_PC
        DeviceTypeUi.Speaker -> DeviceType.SPEAKER
        DeviceTypeUi.Console -> DeviceType.CONSOLE
        DeviceTypeUi.MobileWifi -> DeviceType.MOBILE_WIFI
        DeviceTypeUi.VideoConferencingSystem -> DeviceType.VIDEO_CONFERENCING_SYSTEM
        DeviceTypeUi.DockingStation -> DeviceType.DOCKING_STATION
        DeviceTypeUi.Microphone -> DeviceType.MICROPHONE
        DeviceTypeUi.TvStand -> DeviceType.TV_STAND
        DeviceTypeUi.PowerDistributionUnitPdu -> DeviceType.POWER_DISTRIBUTION_UNIT_PDU
        DeviceTypeUi.Laptop -> DeviceType.LAPTOP
        DeviceTypeUi.Phone -> DeviceType.PHONE
        DeviceTypeUi.Pc -> DeviceType.PC
        DeviceTypeUi.Headphones -> DeviceType.HEADPHONES
        DeviceTypeUi.CardReader -> DeviceType.CARD_READER
        DeviceTypeUi.PremiumKeyboard -> DeviceType.PREMIUM_KEYBOARD
        DeviceTypeUi.Trackpad -> DeviceType.TRACKPAD
        DeviceTypeUi.Memory -> DeviceType.MEMORY
        DeviceTypeUi.PremiumMouse -> DeviceType.PREMIUM_MOUSE
        DeviceTypeUi.Headset -> DeviceType.HEADSET
        DeviceTypeUi.PowerSupply -> DeviceType.POWER_SUPPLY
        DeviceTypeUi.PremiumWebcam -> DeviceType.PREMIUM_WEBCAM
        DeviceTypeUi.ExternalHdd -> DeviceType.EXTERNAL_HDD
        DeviceTypeUi.GraphicsCard -> DeviceType.GRAPHICS_CARD
        DeviceTypeUi.Smartwatch -> DeviceType.SMARTWATCH
        DeviceTypeUi.Firewire -> DeviceType.FIREWIRE
        DeviceTypeUi.DigitizerTablet -> DeviceType.DIGITIZER_TABLET
        DeviceTypeUi.HomeTheater -> DeviceType.HOME_THEATER
        DeviceTypeUi.IndustrialPc -> DeviceType.INDUSTRIAL_PC
        DeviceTypeUi.VrGlasses -> DeviceType.VR_GLASSES
        DeviceTypeUi.ExternalDrive -> DeviceType.EXTERNAL_DRIVE
        DeviceTypeUi.Projector -> DeviceType.PROJECTOR
        DeviceTypeUi.Battery -> DeviceType.BATTERY
        DeviceTypeUi.Cable -> DeviceType.CABLE
        DeviceTypeUi.VideoSplitter -> DeviceType.VIDEO_SPLITTER
        DeviceTypeUi.Pen -> DeviceType.PEN
        DeviceTypeUi.Storage -> DeviceType.STORAGE
        DeviceTypeUi.SecurityKey -> DeviceType.SECURITY_KEY
        DeviceTypeUi.Chromecast -> DeviceType.CHROMECAST
    }
}