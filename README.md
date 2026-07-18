# Torchit NavScan (BLE Prototype)

A modern, high-performance Android Bluetooth Low Energy (BLE) scanning application designed as a hardware diagnostic companion tool for Torchit's assistive mobility devices (such as the Saarthi Smart Cane).

## 🚀 Key Features

* **Modern Security Architecture**: Implements fully bundled Android 12+ runtime hardware clearances (`BLUETOOTH_SCAN`, `BLUETOOTH_CONNECT`) alongside precise and coarse background tracking checks (`ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`).
* **Power-Safe Scanner Pipeline**: Integrates an explicit 10-second `Handler` scan timeout countdown loop using clean Java lambdas to prevent device thread locking and accidental hardware battery drain.
* **Proactive State Interception**: Features transaction-gate hardware checking via `bluetoothAdapter.isEnabled()` to safely block dead antenna scanning loops and guide users via responsive UI Toast alerts.
* **Accessible Visual Design**: Styled completely using contemporary Material 3 container variables and high-contrast styling tokens. Contains embedded `contentDescription` text nodes across all widgets to provide complete, out-of-the-box integration for Google TalkBack assistive screen readers.
* **Simulated Fallback Mode**: Contains an embedded mock-data layer that automatically populates the device list with virtual hardware signals (`Saarthi Smart Cane (Simulated)`) upon activation to facilitate isolated UI layout configuration testing.

## 🛠️ Tech Stack & Components

* **Language & SDK**: Java (JDK 8+ syntax / Lambda execution profiles)
* **UI Architecture**: Structured, highly performant `LinearLayout` stack running a live asynchronous data conveyor `ArrayAdapter` bound to a native scrolling `ListView`.
* **Hardware Engine**: Asynchronous, non-blocking background scanner utilizing native Google `BluetoothLeScanner` and decoupled event-driven `ScanCallback` interface listeners.
