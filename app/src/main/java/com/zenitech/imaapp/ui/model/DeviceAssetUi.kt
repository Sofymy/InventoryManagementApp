package com.zenitech.imaapp.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.Dvr
import androidx.compose.material.icons.twotone.BatteryFull
import androidx.compose.material.icons.twotone.Cable
import androidx.compose.material.icons.twotone.CameraAlt
import androidx.compose.material.icons.twotone.Cast
import androidx.compose.material.icons.twotone.CellWifi
import androidx.compose.material.icons.twotone.CreditCard
import androidx.compose.material.icons.twotone.DesktopWindows
import androidx.compose.material.icons.twotone.DeviceHub
import androidx.compose.material.icons.twotone.Devices
import androidx.compose.material.icons.twotone.Dock
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material.icons.twotone.GraphicEq
import androidx.compose.material.icons.twotone.Headset
import androidx.compose.material.icons.twotone.HeadsetMic
import androidx.compose.material.icons.twotone.Keyboard
import androidx.compose.material.icons.twotone.Laptop
import androidx.compose.material.icons.twotone.Memory
import androidx.compose.material.icons.twotone.Mic
import androidx.compose.material.icons.twotone.Monitor
import androidx.compose.material.icons.twotone.Mouse
import androidx.compose.material.icons.twotone.PhoneAndroid
import androidx.compose.material.icons.twotone.Power
import androidx.compose.material.icons.twotone.Print
import androidx.compose.material.icons.twotone.Router
import androidx.compose.material.icons.twotone.Security
import androidx.compose.material.icons.twotone.Speaker
import androidx.compose.material.icons.twotone.SpeakerPhone
import androidx.compose.material.icons.twotone.Storage
import androidx.compose.material.icons.twotone.SwitchLeft
import androidx.compose.material.icons.twotone.TabletAndroid
import androidx.compose.material.icons.twotone.TabletMac
import androidx.compose.material.icons.twotone.Theaters
import androidx.compose.material.icons.twotone.TouchApp
import androidx.compose.material.icons.twotone.Tv
import androidx.compose.material.icons.twotone.VideoSettings
import androidx.compose.material.icons.twotone.Videocam
import androidx.compose.material.icons.twotone.VideogameAsset
import androidx.compose.material.icons.twotone.ViewModule
import androidx.compose.material.icons.twotone.Vrpano
import androidx.compose.material.icons.twotone.Watch
import androidx.compose.material.icons.twotone.WifiCalling
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
enum class DeviceAssetUi(
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
    Phone(Icons.TwoTone.PhoneAndroid, "Phone"),
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