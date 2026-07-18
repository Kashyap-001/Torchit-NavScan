package com.example.torchitnavscan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private android.bluetooth.le.BluetoothLeScanner bluetoothLeScanner;
    private boolean scanning;

    private final Handler handler = new Handler(android.os.Looper.getMainLooper());

    private static final long SCAN_PERIOD = 10000; // 10-second timeout configuration

    // 2. UI Layout Data Holders
    private java.util.ArrayList<String> deviceList;
    private android.widget.ArrayAdapter<String> listAdapter;
    private android.widget.Button btnScan;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // BIND THE XML VISUAL ELEMENTS TO JAVA
        btnScan = findViewById(R.id.btnScan);
        android.widget.ListView lstDevices = findViewById(R.id.lstDevices);

        // SETUP LIST DATABASES
        deviceList = new java.util.ArrayList<>();
        listAdapter = new android.widget.ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceList);
        lstDevices.setAdapter(listAdapter);

        // SAFELY DISCOVER THE SYSTEM BLUETOOTH HARDWARE
        android.bluetooth.BluetoothManager bluetoothManager = (android.bluetooth.BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager != null) {
            // 1. Bluetooth System Variables
            android.bluetooth.BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
            if (bluetoothAdapter != null) {
                bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
            }
        }

        //  SET THE BUTTON ACTION
        btnScan.setOnClickListener(v -> scanLeDevice());

    }
    @RequiresApi(api = Build.VERSION_CODES.S)
    @SuppressLint("SetTextI18n")
    private void scanLeDevice() {
        // 1. NEW SAFETY LOOPHOLE FIX: Check if the device's Bluetooth is physically turned ON
        android.bluetooth.BluetoothManager bluetoothManager = (android.bluetooth.BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager != null) {
            android.bluetooth.BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

            // If Bluetooth is turned off in settings, stop right here!
            if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
                android.widget.Toast.makeText(this, "Please turn on Bluetooth in your phone settings!", android.widget.Toast.LENGTH_LONG).show();
                return;
            }
        }

        // 2. SECURITY CHECK: Validate location permissions (Your existing code below)
        if (androidx.core.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED ||
                androidx.core.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED) {

            androidx.core.app.ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1);
            return;
        }
        // 2. SECURITY CHECK: Validate modern Location AND Bluetooth Scan permissions together
        if (androidx.core.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED ||
                androidx.core.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED ||
                androidx.core.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) != android.content.pm.PackageManager.PERMISSION_GRANTED ||
                androidx.core.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != android.content.pm.PackageManager.PERMISSION_GRANTED) {

            // Bundles all modern runtime hardware clearances into a single system popup panel
            androidx.core.app.ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.BLUETOOTH_SCAN,
                    android.Manifest.permission.BLUETOOTH_CONNECT
            }, 1);
            return;
        }





        if (!scanning) {
            // SETUP ACTIVE SCAN STATE
            deviceList.clear();

            // EMERGENCY SAFETY NET: Print mock device instantly for validation
            deviceList.add("Saarthi Smart Cane (Simulated)\n00:11:22:33:44:55");
            listAdapter.notifyDataSetChanged();

            // THE 10-SECOND TIMER
            handler.postDelayed(() -> {
                scanning = false;
                if (bluetoothLeScanner != null && androidx.core.app.ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.BLUETOOTH_SCAN) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    bluetoothLeScanner.stopScan(leScanCallback);
                }
                btnScan.setText("Start Hardware Scan"); // Reset button state
            }, SCAN_PERIOD);

            scanning = true;
            if (bluetoothLeScanner != null && androidx.core.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                bluetoothLeScanner.startScan(leScanCallback);
            }
            btnScan.setText("Scanning... Stop");

        } else {
            // D. MANUALLY STOP SCAN STATE
            scanning = false;
            if (bluetoothLeScanner != null && androidx.core.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                bluetoothLeScanner.stopScan(leScanCallback);
            }
            btnScan.setText("Start Hardware Scan");
            handler.removeCallbacksAndMessages(null); // Cancel timer if stopped early
        }
    }
    // This callback object intercepts broadcasting wireless signals in real-time
    private final android.bluetooth.le.ScanCallback leScanCallback = new android.bluetooth.le.ScanCallback() {
        @Override
        public void onScanResult(int callbackType, android.bluetooth.le.ScanResult result) {
            android.bluetooth.BluetoothDevice device = result.getDevice();

            // Runtime security clearance validation check wrapper
            if (androidx.core.app.ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                String name = device.getName();
                String address = device.getAddress();

                // If a device doesn't broadcast a name, label it clearly
                String displayString = (name != null ? name : "Unknown Hardware Device") + "\n" + address;

                // De-duplicate discovered signatures before posting them to the user list
                if (!deviceList.contains(displayString)) {
                    deviceList.add(displayString);
                    listAdapter.notifyDataSetChanged(); // Forces list layout to redraw with new data
                }
            }
        }
    };

}